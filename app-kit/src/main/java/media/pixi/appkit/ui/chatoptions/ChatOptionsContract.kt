package media.pixi.appkit.ui.chatoptions

import android.app.Activity
import media.pixi.appkit.ui.BasePresenter
import media.pixi.appkit.ui.BaseView

interface ChatOptionsContract {

    interface View : BaseView<Presenter> {

    }

    interface Presenter : BasePresenter<View> {
        fun onLocationClicked(activity: Activity)
        fun onVideoClicked(activity: Activity)
        fun onImageClicked(activity: Activity)
    }

    interface Navigator {
        fun showImageChooser(activity: Activity)
        fun showVideoChosser(activity: Activity)
    }
}