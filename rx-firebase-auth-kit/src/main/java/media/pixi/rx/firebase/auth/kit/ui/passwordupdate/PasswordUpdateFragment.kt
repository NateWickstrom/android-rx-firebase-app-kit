package media.pixi.rx.firebase.auth.kit.ui.passwordupdate

import dagger.android.support.DaggerFragment
import javax.inject.Inject

class PasswordUpdateFragment @Inject constructor(): DaggerFragment(), PasswordUpdateContract.View {

    lateinit var presenter: PasswordUpdateContract.Presenter
        @Inject set

}