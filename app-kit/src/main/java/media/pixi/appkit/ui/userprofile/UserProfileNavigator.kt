package media.pixi.appkit.ui.userprofile

import android.app.Activity
import media.pixi.appkit.ui.friends.FriendsActivity
import javax.inject.Inject

class UserProfileNavigator @Inject constructor(): UserProfileContract.Navigator {

    override fun showFriendsScreen(activity: Activity, forUserId: String) {
        FriendsActivity.launch(activity, forUserId)
    }

}