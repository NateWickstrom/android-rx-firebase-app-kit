package media.pixi.appkit.ui.notifications

import media.pixi.appkit.domain.notifications.GetNotifications
import javax.inject.Inject

class NotificationsPresenter @Inject constructor(private var getNotifications: GetNotifications,
                                                 private var navigator: NotificationsNavigator): NotificationsContract.Presenter {

    var view: NotificationsContract.View? = null

    override fun takeView(view: NotificationsContract.View) {
        this.view = view
    }

    override fun dropView() {
        view = null
    }
}