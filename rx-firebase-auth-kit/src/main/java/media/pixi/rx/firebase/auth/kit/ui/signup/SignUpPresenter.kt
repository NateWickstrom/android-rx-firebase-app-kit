package media.pixi.rx.firebase.auth.kit.ui.signup

import android.app.Activity
import io.reactivex.disposables.Disposable
import media.pixi.rx.firebase.auth.kit.data.AuthProvider
import javax.inject.Inject

class SignUpPresenter @Inject constructor(
    private var authProvider: AuthProvider,
    private var navigator: SignUpNavigator): SignUpContract.Presenter {

    private var view: SignUpContract.View? = null

    private var email: String = ""
    private var password: String = ""

    private var disposable: Disposable? = null

    override fun takeView(view: SignUpContract.View) {
        this.view = view
    }

    override fun dropView() {
        this.view = null
        disposable?.dispose()
    }

    override fun onEmailTextChanged(email: String) {
        this.email = email
    }

    override fun onPasswordTextChanged(password: String) {
        this.password = password
    }

    override fun onSignUpClicked(activity: Activity) {
        disposable?.dispose()
        disposable = authProvider.signUp(email, password)
            .subscribe(
                { navigator.onExit(activity) },
                { onError(it) }
            )
    }

    private fun onError(error: Throwable) {
        view?.error = error.message ?: "Unknown error occurred"
    }
}