package media.pixi.rx.firebase.auth.kit.ui.account

import dagger.android.support.DaggerFragment
import javax.inject.Inject

class PasswordForgotFragment @Inject constructor(): DaggerFragment(), PasswordForgotContract.View {

    @Inject var presenter: PasswordForgotContract.Presenter? = null

}