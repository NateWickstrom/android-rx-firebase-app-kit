package media.pixi.appkit.ui.chatcreator

import android.app.Activity
import io.reactivex.disposables.CompositeDisposable
import media.pixi.appkit.data.auth.AuthProvider
import media.pixi.appkit.data.profile.UserProfile
import media.pixi.appkit.domain.users.ComparatorUserProfile
import media.pixi.appkit.domain.users.GetFriends
import timber.log.Timber
import javax.inject.Inject

class ChatCreatorPresenter @Inject constructor(private var getFriends: GetFriends,
                                               private var authProvider: AuthProvider,
                                               private var navigator: ChatCreatorContract.Navigator): ChatCreatorContract.Presenter {

    private val selected: MutableSet<UserProfile> = mutableSetOf()
    private val contacts: MutableSet<UserProfile> = mutableSetOf()

    private var view: ChatCreatorContract.View? = null
    private var disposables = CompositeDisposable()
    private var filter: String? = null

    override fun takeView(view: ChatCreatorContract.View) {
        this.view = view

        view.loading = true

        disposables.add(getFriends.getFriends().subscribe(
            { onResult(it) },
            { onError(it) },
            { onComplete() }
        ))

        //loadFakeContacts()
    }

    override fun dropView() {
        disposables.clear()
        view = null
    }

    override fun onListItemClicked(activity: Activity, userProfile: UserProfile) {
        if (selected.contains(userProfile)) {
            selected.remove(userProfile)
        } else {
            selected.add(userProfile)
        }
        view?.setSelectedContacts(selected)
        view?.canCreate = selected.isNotEmpty()
    }

    override fun onRemoveContactClicked(activity: Activity, userProfile: UserProfile) {
        selected.remove(userProfile)
        view?.setSelectedContacts(selected)
        view?.canCreate = selected.isNotEmpty()
    }

    override fun onSelectedContactClicked(activity: Activity, userProfile: UserProfile) {
        navigator.showUserProfile(activity, userProfile.id)
    }

    override fun onCreateChatClicked(activity: Activity) {
        val userIds = arrayListOf<CharSequence>()
        userIds.addAll(selected.map { it.id })
        userIds.add(authProvider.getUserId()!!)
        navigator.showNewChat(activity, userIds)
    }

    override fun onTextChanged(query: String) {
        filter = query
        filterContacts()
    }

    private fun onResult(friends: List<UserProfile>) {
        view?.loading = false
        contacts.clear()
        contacts.addAll(friends)
        view?.showNoResults(friends.isEmpty())

        view?.setContacts(friends)

        selected.clear()
        view?.setSelectedContacts(selected)
    }

    private fun onError(error: Throwable) {
        view?.loading = false
        view?.error = error.message.toString()
        view?.showNoResults(contacts.isEmpty())
        Timber.e(error)
    }

    private fun onComplete() {
        view?.loading = false
        view?.showNoResults(contacts.isEmpty())
    }

    private fun filterContacts() {
        filter?.let { filter ->
            val filtered = contacts.filter { it.username.startsWith( filter, true) }
            view?.setContacts(filtered)
        }

    }

    private fun sort(users: List<UserProfile>): List<UserProfile> {
        val mutableChats = users.toMutableList()
        mutableChats.sortWith(ComparatorUserProfile())
        return mutableChats
    }

    private fun loadFakeContacts() {
        val users = mutableListOf(
            UserProfile(
                id = "some_id",
                friendCount = 10,
                username = "sting@gmail.com",
                firstName = "Sting",
                lastName = "",
                imageUrl = ""
            ),
            UserProfile(
                id = "some_id",
                friendCount = 10,
                username = "david.bowie",
                firstName = "David",
                lastName = "Bowie",
                imageUrl = ""
            ),
            UserProfile(
                id = "some_id",
                friendCount = 10,
                username = "travis.daily",
                firstName = "Travis",
                lastName = "Daily",
                imageUrl = ""
            ),
            UserProfile(
                id = "some_id",
                friendCount = 10,
                username = "matt.bower",
                firstName = "Matt",
                lastName = "Bower",
                imageUrl = ""
            ),
            UserProfile(
                id = "some_id",
                friendCount = 10,
                username = "sarah.hawkins",
                firstName = "Sarah",
                lastName = "Hawkins",
                imageUrl = ""
            ),
            UserProfile(
                id = "some_id",
                friendCount = 10,
                username = "Seth.Green",
                firstName = "Seth",
                lastName = "Green",
                imageUrl = ""
            )
        )
        onResult(users)
    }
}