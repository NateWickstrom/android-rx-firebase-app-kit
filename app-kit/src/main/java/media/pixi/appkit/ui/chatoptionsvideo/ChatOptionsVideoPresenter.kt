package media.pixi.appkit.ui.chatoptionsvideo

import android.app.Activity
import javax.inject.Inject

class ChatOptionsVideoPresenter @Inject constructor(val navigator: ChatOptionsVideoContract.Navigator): ChatOptionsVideoContract.Presenter {

    override fun takeView(view: ChatOptionsVideoContract.View) {

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