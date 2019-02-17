package media.pixi.appkit.ui.signin

import android.app.Activity
import android.content.Intent
import media.pixi.appkit.ui.BasePresenter
import media.pixi.appkit.ui.BaseView

interface SignInContract {

    interface View: BaseView<Presenter> {
        var loading: Boolean
    }

    interface Presenter: BasePresenter<View> {
        fun onEmailTextChanged(email: String)
        fun onPasswordTextChanged(password: String)
        fun onForgotPasswordClicked(activity: Activity)
        fun onSignInClicked(activity: Activity)
        fun onSignUpClicked(activity: Activity)
    }

    interface Navigator {
        fun showForgotPasswordScreen(activity: Activity)
        fun showSignUpScreen(activity: Activity)
        fun onLoggedInSuccessfully(activity: Activity)
        fun onActivityResult(activity: Activity, requestCode: Int, resultCode: Int, data: Intent?)
    }
}