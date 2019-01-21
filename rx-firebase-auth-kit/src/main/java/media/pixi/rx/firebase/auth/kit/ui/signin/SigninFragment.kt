package media.pixi.rx.firebase.auth.kit.ui.signin

import dagger.android.support.DaggerFragment
import javax.inject.Inject

class SigninFragment @Inject constructor(): DaggerFragment(), SigninContract.View {

    @Inject var presenter: SigninContract.Presenter? = null

}