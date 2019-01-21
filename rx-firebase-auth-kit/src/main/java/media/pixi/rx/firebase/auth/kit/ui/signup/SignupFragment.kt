package media.pixi.rx.firebase.auth.kit.ui.signup

import dagger.android.support.DaggerFragment
import media.pixi.rx.firebase.auth.kit.ui.signin.SigninContract
import javax.inject.Inject

class SignupFragment @Inject constructor(): DaggerFragment(), SignupContract.View {

    var presenter: SigninContract.Presenter? = null
        @Inject set

}