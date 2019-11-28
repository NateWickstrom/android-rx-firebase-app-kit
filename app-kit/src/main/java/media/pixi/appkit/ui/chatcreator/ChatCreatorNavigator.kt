package media.pixi.appkit.ui.chatcreator

import android.app.Activity
import media.pixi.appkit.ui.chat.ChatActivity
import media.pixi.appkit.ui.userprofile.UserProfileActivity
import javax.inject.Inject

class ChatCreatorNavigator  @Inject constructor(): ChatCreatorContract.Navigator {

    override fun showNewChat(activity: Activity, userIds: ArrayList<CharSequence>) {
        ChatActivity.launch(activity, null, userIds)
    }

    override fun showUserProfile(activity: Activity, userId: String) {
        UserProfileActivity.launch(activity, userId)
    }
}