package media.pixi.appkit.example.ui.home

import android.app.Activity
import android.content.Intent
import media.pixi.rx.algolia.search.ui.search.SearchActivity
import media.pixi.rx.firebase.profile.kit.ui.profile.ProfileActivity
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