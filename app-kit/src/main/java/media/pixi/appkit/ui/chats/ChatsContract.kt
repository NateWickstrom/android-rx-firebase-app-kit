package media.pixi.appkit.ui.chats

import android.app.Activity
import media.pixi.appkit.ui.BasePresenter
import media.pixi.appkit.ui.BaseView

interface ChatsContract {

    interface View: BaseView<Presenter> {

    }

    interface Presenter: BasePresenter<View> {
        fun onNewChatClicked(activity: Activity)
    }

    interface Navigator {
        fun showChatCreator(activity: Activity)
    }
}