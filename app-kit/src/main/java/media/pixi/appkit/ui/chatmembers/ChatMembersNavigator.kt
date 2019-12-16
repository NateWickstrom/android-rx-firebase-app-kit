package media.pixi.appkit.ui.chatmembers

import android.app.Activity
import android.content.Intent
import media.pixi.appkit.ui.myprofile.ProfileActivity
import media.pixi.appkit.ui.userprofile.UserProfileActivity
import javax.inject.Inject

class ChatMembersNavigator @Inject constructor(): ChatMembersContract.Navigator {

    override fun showMyProfilecreen(activity: Activity) {
        val intent = Intent(activity, ProfileActivity::class.java)
        activity.startActivity(intent)
    }

    override fun showUserProfileScreen(activity: Activity, userId: String) {
        UserProfileActivity.launch(activity, userId)
    }

}
