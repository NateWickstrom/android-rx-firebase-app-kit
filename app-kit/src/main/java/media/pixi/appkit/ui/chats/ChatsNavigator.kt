package media.pixi.appkit.ui.chats

import android.app.Activity
import media.pixi.appkit.ui.chatcreator.ChatCreatorActivity
import javax.inject.Inject

class ChatsNavigator @Inject constructor(): ChatsContract.Navigator {

    override fun showChatCreator(activity: Activity) {
        ChatCreatorActivity.launch(activity)
    }

}