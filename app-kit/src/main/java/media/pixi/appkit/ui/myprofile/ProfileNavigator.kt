package media.pixi.appkit.ui.myprofile

import android.app.Activity
import media.pixi.appkit.ui.friends.FriendsActivity
import javax.inject.Inject

class ProfileNavigator @Inject constructor(): ProfileContract.Navigator {

    override fun showFriendsScreen(activity: Activity, forUserId: String) {
        FriendsActivity.launch(activity, forUserId)
    }

}