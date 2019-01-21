package media.pixi.rx.firebase.auth.kit.ui.passwordforgot

import dagger.android.support.DaggerFragment
import javax.inject.Inject

class PasswordForgotFragment @Inject constructor(): DaggerFragment(), PasswordForgotContract.View {

    var presenter: PasswordForgotContract.Presenter? = null
        @Inject set

}