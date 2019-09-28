package media.pixi.appkit.ui.notifications

import io.reactivex.disposables.Disposable
import media.pixi.appkit.domain.notifications.GetNotifications
import media.pixi.appkit.domain.notifications.Notification
import javax.inject.Inject

class NotificationsPresenter @Inject constructor(private var getNotifications: GetNotifications,
                                                 private var navigator: NotificationsNavigator): NotificationsContract.Presenter {

    private var view: NotificationsContract.View? = null
    private var disposable: Disposable? = null

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
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemLongClicked(position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onActionLongClicked(position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun onResult(results: List<Notification>) {
        view?.setResults(results)
        view?.loading = false
    }

    private fun onComplete() {
        view?.loading = false
    }

    private fun onError(error: Throwable) {
        view?.error = error.message?: "Oops, error"
        view?.loading = false
    }
}