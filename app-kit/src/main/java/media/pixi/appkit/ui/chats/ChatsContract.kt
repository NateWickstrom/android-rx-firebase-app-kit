package media.pixi.appkit.ui.chats

import media.pixi.appkit.ui.BasePresenter
import media.pixi.appkit.ui.BaseView

interface ChatsContract {

    interface View: BaseView<Presenter> {

    }

    interface Presenter: BasePresenter<View> {

    }

    interface Navigator {

    }
}