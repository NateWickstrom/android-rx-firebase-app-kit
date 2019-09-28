package media.pixi.appkit.ui.notifications

import media.pixi.appkit.domain.notifications.Notification
import media.pixi.appkit.ui.BasePresenter
import media.pixi.appkit.ui.BaseView

interface NotificationsContract {

    interface View: BaseView<Presenter> {
        var loading: Boolean

        fun setResults(results: List<Notification>)
    }

    interface Presenter: BasePresenter<View> {

    }

    interface Navigator {

    }
}