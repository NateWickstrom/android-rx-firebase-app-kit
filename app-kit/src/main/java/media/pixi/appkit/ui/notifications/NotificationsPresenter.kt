package media.pixi.appkit.ui.notifications

import io.reactivex.disposables.Disposable
import media.pixi.appkit.domain.notifications.GetNotifications
import media.pixi.appkit.domain.notifications.Notification
import timber.log.Timber
import javax.inject.Inject

class NotificationsPresenter @Inject constructor(private var getNotifications: GetNotifications,
                                                 private var navigator: NotificationsNavigator): NotificationsContract.Presenter {

    private var view: NotificationsContract.View? = null
    private var disposable: Disposable? = null
    private var notifications: List<Notification>? = null

    override fun takeView(view: NotificationsContract.View) {
        this.view = view
        view.loading = true

        disposable = getNotifications.getNotifications()
            .subscribe(
                this::onResult,
                this::onError,
                this::onComplete
            )
    }

    override fun dropView() {
        view = null
        disposable?.dispose()
    }

    override fun onItemClicked(position: Int) {

    }

    override fun onItemLongClicked(position: Int) {

    }

    override fun onActionLongClicked(position: Int) {

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