package media.pixi.appkit.ui.signup

import android.app.Activity
import media.pixi.appkit.ui.BasePresenter
import media.pixi.appkit.ui.BaseView

interface SignUpContract {

    interface View: BaseView<Presenter> {
        var loading: Boolean
        var enableSignupButton: Boolean
    }

    interface Presenter: BasePresenter<View> {
        fun onFirstNameTextChanged(firstName: String)
        fun onLastNameTextChanged(lastName: String)
        fun onEmailTextChanged(email: String)
        fun onPasswordTextChanged(password: String)

        fun onSignUpClicked(activity: Activity)
    }

    interface Navigator {
        fun onLoggedInSuccessfully(activity: Activity)
    }
}