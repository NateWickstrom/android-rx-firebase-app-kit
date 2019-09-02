package media.pixi.appkit.ui.friends

import android.app.Activity
import media.pixi.appkit.ui.friend.FriendActivity
import javax.inject.Inject

class FriendsNavigator @Inject constructor(): FriendsContract.Navigator {

    override fun showFriendScreen(activity: Activity, userId: String) {
        FriendActivity.launch(activity, userId)
    }

}
