package media.pixi.appkit.ui.chatoptionsimage

import android.app.Activity
import javax.inject.Inject

class ChatOptionsImagePresenter @Inject constructor(val navigator: ChatOptionsImageContract.Navigator): ChatOptionsImageContract.Presenter {

    override fun takeView(view: ChatOptionsImageContract.View) {

    }

    override fun dropView() {

    }

    override fun onGalleryClicked(activity: Activity) {
        navigator.showGallery(activity)
    }

    override fun onCameraClicked(activity: Activity) {
        navigator.showCamera(activity)
    }
}