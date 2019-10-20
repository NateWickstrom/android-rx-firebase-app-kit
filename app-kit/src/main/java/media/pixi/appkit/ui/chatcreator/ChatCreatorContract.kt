package media.pixi.appkit.ui.chatcreator

import media.pixi.appkit.ui.BasePresenter
import media.pixi.appkit.ui.BaseView

interface ChatCreatorContract {

    interface View: BaseView<Presenter> {

    }

    interface Presenter: BasePresenter<View> {

    }

    interface Navigator {

    }
}