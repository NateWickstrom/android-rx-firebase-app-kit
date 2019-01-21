package media.pixi.rx.firebase.auth.kit.ui.signup

import media.pixi.rx.firebase.auth.kit.data.AuthProvider
import javax.inject.Inject

class SignupPresenter @Inject constructor(): SignupContract.Presenter {

    lateinit var authProvider: AuthProvider
        @Inject set

    override fun takeView(veiew: SignupContract.View) {
    }

    override fun dropView() {
    }
}