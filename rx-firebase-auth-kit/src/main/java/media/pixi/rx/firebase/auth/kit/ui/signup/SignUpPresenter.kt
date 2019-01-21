package media.pixi.rx.firebase.auth.kit.ui.signup

import android.app.Activity
import media.pixi.rx.firebase.auth.kit.data.AuthProvider
import javax.inject.Inject

class SignUpPresenter @Inject constructor(
    private var authProvider: AuthProvider): SignUpContract.Presenter {

    override fun takeView(veiew: SignUpContract.View) {
    }

    override fun dropView() {
    }

    override fun onEmailTextChanged(email: String) {

    }

    override fun onPasswordTextChanged(email: String) {

    }

    override fun onSignUpClicked(activity: Activity) {

    }
}