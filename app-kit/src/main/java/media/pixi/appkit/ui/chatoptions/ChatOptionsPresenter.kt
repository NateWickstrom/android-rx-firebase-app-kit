package media.pixi.appkit.ui.chatoptions

import android.app.Activity
import javax.inject.Inject

class ChatOptionsPresenter @Inject constructor(val navigator: ChatOptionsContract.Navigator): ChatOptionsContract.Presenter {

    override fun takeView(view: ChatOptionsContract.View) {

    }

    override fun dropView() {

    }

    override fun onLocationClicked(activity: Activity) {

    }

    override fun onVideoClicked(activity: Activity) {
        navigator.showVideoChosser(activity)
    }


    override fun onImageClicked(activity: Activity) {
        navigator.showImageChooser(activity)
    }
}