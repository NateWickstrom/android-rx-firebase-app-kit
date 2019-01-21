package media.pixi.rx.firebase.auth.kit.ui.signup

import media.pixi.rx.firebase.auth.kit.data.AuthProvider
import javax.inject.Inject

class SignUpPresenter @Inject constructor(): SignUpContract.Presenter {

    lateinit var authProvider: AuthProvider
        @Inject set

    override fun takeView(veiew: SignUpContract.View) {
    }

    override fun dropView() {
    }
}