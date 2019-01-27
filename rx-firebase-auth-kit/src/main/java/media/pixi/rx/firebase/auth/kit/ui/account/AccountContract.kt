package media.pixi.rx.firebase.auth.kit.ui.account

import android.app.Activity
import media.pixi.rx.firebase.auth.kit.ui.BasePresenter
import media.pixi.rx.firebase.auth.kit.ui.BaseView

interface AccountContract {

    interface View: BaseView<Presenter> {
        var username: String
        var email: String
    }

    interface Presenter: BasePresenter<View> {
        fun onSaveClicked(activity: Activity)
        fun onSignOutClicked(activity: Activity)
        fun onResetClicked(activity: Activity)
        fun onUpdatePasswordClicked(activity: Activity)
        fun onVerifyEmailClicked(activity: Activity)
    }

    interface Navigator {
        fun showUpdatePasswordScreen(activity: Activity)
        fun showSignInScreen(activity: Activity)
    }
}