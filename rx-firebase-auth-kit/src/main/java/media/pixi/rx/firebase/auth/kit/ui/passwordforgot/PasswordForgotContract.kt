package media.pixi.rx.firebase.auth.kit.ui.passwordforgot

import android.app.Activity
import media.pixi.rx.firebase.auth.kit.ui.BasePresenter
import media.pixi.rx.firebase.auth.kit.ui.BaseView

interface PasswordForgotContract {

    interface View: BaseView<Presenter> {

    }

    interface Presenter: BasePresenter<View> {
        fun onSendClicked(activity: Activity)
    }

    interface Navigator {
        fun onExit(activity: Activity)
    }
}