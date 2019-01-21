package media.pixi.rx.firebase.auth.kit.ui.signin

import android.app.Activity
import media.pixi.rx.firebase.auth.kit.ui.BasePresenter
import media.pixi.rx.firebase.auth.kit.ui.BaseView

interface SignInContract {

    interface View: BaseView<Presenter> {

    }

    interface Presenter: BasePresenter<View> {
        fun onForgotPasswordClicked(activity: Activity)
        fun onSignUpClicked(activity: Activity)
    }

    interface Navigator {
        fun showForgotPasswordScreen(activity: Activity)
        fun showSignUpScreen(activity: Activity)
    }
}