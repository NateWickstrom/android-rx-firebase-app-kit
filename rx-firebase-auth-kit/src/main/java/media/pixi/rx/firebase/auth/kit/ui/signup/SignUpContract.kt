package media.pixi.rx.firebase.auth.kit.ui.signup

import android.app.Activity
import media.pixi.rx.firebase.auth.kit.ui.BasePresenter
import media.pixi.rx.firebase.auth.kit.ui.BaseView

interface SignUpContract {

    interface View: BaseView<Presenter> {

    }

    interface Presenter: BasePresenter<View> {
        fun onEmailTextChanged(email: String)
        fun onPasswordTextChanged(password: String)
        fun onSignUpClicked(activity: Activity)
    }

    interface Navigator {
        fun onExit(activity: Activity)
    }
}