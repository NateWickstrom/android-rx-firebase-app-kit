package media.pixi.appkit.ui.chatoptionsvideo

import android.app.Activity
import media.pixi.appkit.ui.BasePresenter
import media.pixi.appkit.ui.BaseView

interface ChatOptionsVideoContract {

    interface View : BaseView<Presenter> {

    }

    interface Presenter : BasePresenter<View> {
        fun onGalleryClicked(activity: Activity)
        fun onCameraClicked(activity: Activity)
    }

    interface Navigator {
        fun showGallery(activity: Activity)
        fun showCamera(activity: Activity)
    }
}