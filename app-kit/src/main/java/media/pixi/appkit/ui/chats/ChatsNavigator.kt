package media.pixi.appkit.ui.chats

import android.app.Activity
import media.pixi.appkit.ui.chat.ChatActivity
import media.pixi.appkit.ui.chatcreator.ChatCreatorActivity
import javax.inject.Inject

class ChatsNavigator @Inject constructor(): ChatsContract.Navigator {

    override fun showChat(activity: Activity, chatId: String) {
        ChatActivity.launch(activity, chatId)
    }

    override fun showChatCreator(activity: Activity) {
        ChatCreatorActivity.launch(activity)
    }

}