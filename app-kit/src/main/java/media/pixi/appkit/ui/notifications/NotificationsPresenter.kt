package media.pixi.appkit.ui.notifications

import android.app.Activity
import io.reactivex.disposables.CompositeDisposable
import media.pixi.appkit.data.notifications.NotificationProvider
import media.pixi.appkit.data.profile.UserProfileProvider
import media.pixi.appkit.domain.notifications.*
import timber.log.Timber
import javax.inject.Inject

class NotificationsPresenter @Inject constructor(
    private var getNotifications: GetNotifications,
    private val addNotifications: AddNotifications,
    private val notificationProvider: NotificationProvider,
    private val acceptFriendRequest: AcceptFriendRequest,
    private var userProfileProvider: UserProfileProvider,
    private val notificationBus: NotificationBus,
    private var navigator: NotificationsNavigator
) : NotificationsContract.Presenter, NotificationBus.NotificationListener {

    private val disposable = CompositeDisposable()

    private var view: NotificationsContract.View? = null
    private var notifications: ArrayList<MyNotification>? = null

    override fun takeView(view: NotificationsContract.View) {
        this.view = view
        view.loading = true

        disposable.add(
            getNotifications.getNotifications()
                .subscribe(
                    this::onResult,
                    this::onError,
                    this::onComplete
                )
        )

        notificationBus.addListener(this)
    }

    override fun dropView() {
        view = null
        disposable.dispose()
        notificationBus.removeListener(this)
    }

    override fun onItemClicked(activity: Activity, notification: MyNotification, position: Int) {
        navigator.showProfile(activity, notification.userProfile)
    }

    override fun onItemLongClicked(
        activity: Activity,
        notification: MyNotification,
        position: Int
    ) {
        navigator.showProfile(activity, notification.userProfile)
    }

    override fun onAcceptFriendRequestClicked(notification: MyNotification, position: Int) {
        view?.loading = true
        disposable.add(
            userProfileProvider.addFriend(notification.userProfile.id)
                .subscribe(
                    { onAcceptFriendRequestComplete(notification, position) },
                    this::onError
                )
        )
    }

    override fun onDeleteNotification(position: Int) {
        notifications?.let {
            view?.loading = true
            val notification = it[position]
            it.removeAt(position)

            view?.showMessage("Deleted MyNotification") {
                add(notification, position)
            }

            // delete notification
            disposable.add(
                notificationProvider.deleteNotification(notification.id)
                    .subscribe(
                        { onDeleteComplete(notification, position) },
                        this::onError
                    )
            )
        }
    }

    override fun onMessageReceived(message: MyNotification): Boolean {
        return true
    }

    private fun onDeleteComplete(notification: MyNotification, position: Int) {
        view?.loading = false
        view?.hasResults = notifications.isNullOrEmpty().not()
    }

    private fun add(notification: MyNotification, position: Int) {
        notifications?.add(position, notification)
        view?.setResults(notifications!!)
        view?.loading = true
        view?.hasResults = notifications.isNullOrEmpty().not()
        disposable.add(
            addNotifications.addNotification(notification)
                .subscribe(
                    { onRestoreComplete() },
                    this::onError
                )
        )
    }

    private fun onAcceptFriendRequestComplete(notification: MyNotification, position: Int) {
        view?.loading = false
        notifications?.removeAt(position)
        view?.hasResults = notifications.isNullOrEmpty().not()
    }

    private fun onResult(results: List<MyNotification>) {
        notifications?.clear()
        notifications = ArrayList(results)
        view?.setResults(results)
        view?.loading = false
        view?.hasResults = notifications.isNullOrEmpty().not()
    }

    private fun onComplete() {
        view?.loading = false
        view?.hasResults = notifications.isNullOrEmpty().not()
    }

    private fun onError(error: Throwable) {
        Timber.e(error)
        view?.error = error.message ?: "Oops, error"
        view?.loading = false
        view?.hasResults = notifications.isNullOrEmpty().not()
    }

    private fun onRestoreComplete() {
        view?.loading = false
        view?.hasResults = notifications.isNullOrEmpty().not()
    }
}