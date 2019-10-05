package media.pixi.appkit.ui.develop

import media.pixi.appkit.ui.BasePresenter
import media.pixi.appkit.ui.BaseView

interface DevelopContract {

    interface View: BaseView<Presenter> {
        var userId: String
        var deviceId: String
    }

    interface Presenter: BasePresenter<View> {

    }

    interface Navigator {

    }
}