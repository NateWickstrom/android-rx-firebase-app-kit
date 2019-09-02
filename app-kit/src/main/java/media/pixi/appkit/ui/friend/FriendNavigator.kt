package media.pixi.appkit.ui.friend

import android.app.Activity
import media.pixi.appkit.ui.friends.FriendsActivity
import javax.inject.Inject

class FriendNavigator @Inject constructor(): FriendContract.Navigator {

    override fun showFriendsScreen(activity: Activity, forUserId: String) {
        FriendsActivity.launch(activity, forUserId)
    }

}