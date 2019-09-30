package media.pixi.appkit.ui.notifications

import android.app.Activity
import io.reactivex.disposables.CompositeDisposable
import media.pixi.appkit.data.profile.UserProfileProvider
import media.pixi.appkit.domain.notifications.GetNotifications
import media.pixi.appkit.domain.notifications.NewFriendNotification
import media.pixi.appkit.domain.notifications.Notification
import timber.log.Timber
import javax.inject.Inject

class NotificationsPresenter @Inject constructor(private var getNotifications: GetNotifications,
                                                 private var userProfileProvider: UserProfileProvider,
                                                 private var navigator: NotificationsNavigator): NotificationsContract.Presenter {

    private val disposable = CompositeDisposable()

    private var view: NotificationsContract.View? = null
    private var notifications: List<Notification>? = null

    override fun takeView(view: NotificationsContract.View) {
        this.view = view
        view.loading = true

        disposable.add(getNotifications.getNotifications()
            .subscribe(
                this::onResult,
                this::onError,
                this::onComplete
            ))
    }

    override fun dropView() {
        view = null
        disposable.dispose()
    }

    override fun onItemClicked(activity: Activity, notification: Notification, position: Int) {
        navigator.showProfile(activity, notification.userProfile)
    }

    override fun onItemLongClicked(activity: Activity, notification: Notification, position: Int) {
        navigator.showProfile(activity, notification.userProfile)
    }

    override fun onActionLongClicked(notification: Notification, position: Int) {
        disposable.add(userProfileProvider.addFriend(notification.userProfile.id)
            .subscribe(
                { onActionComplete(notification, position) },
                this::onError
            ))
    }

    override fun onItemDeleted(position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun onActionComplete(notification: Notification, position: Int) {
        view?.set(position, NewFriendNotification(
            imageUrl = notification.imageUrl,
            title = "You and ${notification.userProfile.firstName} are now friends",
            subtitle = "",
            userProfile = notification.userProfile
        ))
    }

    private fun onResult(results: List<Notification>) {
        notifications = results
        view?.setResults(results)
        view?.loading = false
    }

    private fun onComplete() {
        view?.loading = false
    }

    private fun onError(error: Throwable) {
        Timber.e(error)
        view?.error = error.message?: "Oops, error"
        view?.loading = false
    }
}