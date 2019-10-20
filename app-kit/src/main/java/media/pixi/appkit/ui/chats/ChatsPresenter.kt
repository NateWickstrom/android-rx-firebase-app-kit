package media.pixi.appkit.ui.chats

import android.app.Activity
import javax.inject.Inject

class ChatsPresenter @Inject constructor(val navigator: ChatsNavigator) : ChatsContract.Presenter {

    override fun takeView(view: ChatsContract.View) {

    }

    override fun dropView() {

    }

    override fun onNewChatClicked(activity: Activity) {
        navigator.showChatCreator(activity)
    }
}