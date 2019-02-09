package media.pixi.appkit.ui.passwordupdate

import android.app.Activity
import media.pixi.appkit.ui.BasePresenter
import media.pixi.appkit.ui.BaseView

interface PasswordUpdateContract {

    interface View: BaseView<Presenter> {

    }

    interface Presenter: BasePresenter<View> {
        fun onUpdateClicked(activity: Activity)
    }

    interface Navigator {
        fun onExit(activity: Activity)
    }
}