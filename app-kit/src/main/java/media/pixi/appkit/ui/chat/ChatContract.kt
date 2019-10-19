package media.pixi.appkit.ui.chat

import media.pixi.appkit.ui.BasePresenter
import media.pixi.appkit.ui.BaseView

interface ChatContract {

    interface View: BaseView<Presenter> {

    }

    interface Presenter: BasePresenter<View> {

    }

    interface Navigator {

    }
}