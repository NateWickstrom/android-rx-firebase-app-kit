package media.pixi.appkit.ui.friends

import android.app.Activity
import android.content.Intent
import media.pixi.appkit.ui.myprofile.ProfileActivity
import media.pixi.appkit.ui.userprofile.UserProfileActivity
import javax.inject.Inject

class FriendsNavigator @Inject constructor(): FriendsContract.Navigator {

    override fun showMyProfilecreen(activity: Activity) {
        val intent = Intent(activity, ProfileActivity::class.java)
        activity.startActivity(intent)
    }

    override fun showFriendScreen(activity: Activity, userId: String) {
        UserProfileActivity.launch(activity, userId)
    }

}
