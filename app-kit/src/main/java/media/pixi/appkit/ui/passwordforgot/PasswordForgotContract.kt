package media.pixi.appkit.ui.passwordforgot

import android.app.Activity
import media.pixi.appkit.ui.BasePresenter
import media.pixi.appkit.ui.BaseView

interface PasswordForgotContract {

    interface View: BaseView<Presenter> {
        var loading: Boolean
        var email: String
    }

    interface Presenter: BasePresenter<View> {
        fun onSendClicked(activity: Activity)
    }

    interface Navigator {
        fun onExit(activity: Activity)
    }
}