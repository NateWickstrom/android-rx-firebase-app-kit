package media.pixi.appkit.ui.signup

import android.app.Activity
import android.content.Context
import io.reactivex.disposables.Disposable
import media.pixi.appkit.R
import media.pixi.appkit.data.auth.AuthProvider
import javax.inject.Inject

class SignUpPresenter @Inject constructor(
    private var authProvider: AuthProvider,
    private var navigator: SignUpNavigator,
    private var context: Context): SignUpContract.Presenter {

    private var view: SignUpContract.View? = null

    private var firstName: String = ""
    private var lastName: String = ""
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

    override fun onFirstNameTextChanged(firstName: String) {
        view?.error  = ""
        this.firstName = firstName
    }

    override fun onLastNameTextChanged(lastName: String) {
        view?.error  = ""
        this.lastName = lastName
    }

    override fun onEmailTextChanged(email: String) {
        view?.error  = ""
        this.email = email
    }

    override fun onPasswordTextChanged(password: String) {
        view?.error  = ""
        this.password = password
    }

    override fun onSignUpClicked(activity: Activity) {
        if (firstName.isBlank()) {
            view?.error = context.getString(R.string.error_first_name_blank)
            return
        }
        if (lastName.isBlank()) {
            view?.error = context.getString(R.string.error_last_name_blank)
            return
        }
        if (email.isBlank()) {
            view?.error = context.getString(R.string.error_email_blank)
            return
        }
        if (password.isBlank()) {
            view?.error = context.getString(R.string.error_password_blank)
            return
        }

        view?.enableSignupButton = false
        view?.loading = true

        disposable?.dispose()
        disposable = authProvider.signUp(firstName, lastName, email, password)
            .subscribe(
                { onResult(activity) },
                { onError(it) }
            )
    }

    private fun onResult(activity: Activity) {
        view?.enableSignupButton = true
        view?.loading = false
        navigator.onLoggedInSuccessfully(activity)
    }

    private fun onError(error: Throwable) {
        view?.enableSignupButton = true
        view?.loading = false
        view?.error = error.message ?: "Unknown error occurred"
    }
}