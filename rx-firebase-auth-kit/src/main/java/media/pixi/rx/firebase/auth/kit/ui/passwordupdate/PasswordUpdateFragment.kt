package media.pixi.rx.firebase.auth.kit.ui.passwordupdate

import dagger.android.support.DaggerFragment
import javax.inject.Inject

class PasswordUpdateFragment @Inject constructor(): DaggerFragment(), PasswordUpdateContract.View {

    var presenter: PasswordUpdateContract.Presenter? = null
        @Inject set

}