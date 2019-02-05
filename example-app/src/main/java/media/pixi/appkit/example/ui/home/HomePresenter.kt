package media.pixi.appkit.example.ui.home

import android.app.Activity
import javax.inject.Inject

class HomePresenter @Inject constructor(private val navigator: HomeNavigator):
    HomeContract.Presenter {

    override fun onProfileClicked(activity: Activity) {
        navigator.showUserProfile(activity)
    }

    override fun onSearchClicked(activity: Activity) {
        navigator.showSearch(activity)
    }
}