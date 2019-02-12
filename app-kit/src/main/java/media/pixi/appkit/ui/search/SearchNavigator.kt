package media.pixi.appkit.ui.search

import android.app.Activity
import android.content.Intent
import media.pixi.appkit.data.search.PersonSearchResult
import media.pixi.appkit.ui.friend.FriendActivity
import javax.inject.Inject

class SearchNavigator @Inject constructor(): SearchContract.Navigator {

    override fun showProfile(activity: Activity, user: PersonSearchResult) {
        val intent = Intent(activity, FriendActivity::class.java)
        activity.startActivity(intent)
    }

}