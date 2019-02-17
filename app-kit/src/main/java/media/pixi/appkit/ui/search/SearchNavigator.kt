package media.pixi.appkit.ui.search

import android.app.Activity
import media.pixi.appkit.data.search.PersonSearchResult
import media.pixi.appkit.ui.friend.FriendActivity
import javax.inject.Inject

class SearchNavigator @Inject constructor(): SearchContract.Navigator {

    override fun showProfile(activity: Activity, user: PersonSearchResult) {
        FriendActivity.launch(activity, user.id)
    }

}