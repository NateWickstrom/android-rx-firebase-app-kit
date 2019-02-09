package media.pixi.appkit.example.ui.home

import android.app.Activity
import android.content.Intent
import media.pixi.appkit.ui.profile.ProfileActivity
import media.pixi.appkit.ui.search.SearchActivity
import javax.inject.Inject

class HomeNavigator @Inject constructor(): HomeContract.Navigator {

    override fun showUserProfile(activity: Activity) {
        val intent = Intent(activity, ProfileActivity::class.java)
        activity.startActivity(intent)
    }

    override fun showSearch(activity: Activity) {
        val intent = Intent(activity, SearchActivity::class.java)
        activity.startActivity(intent)
    }
}