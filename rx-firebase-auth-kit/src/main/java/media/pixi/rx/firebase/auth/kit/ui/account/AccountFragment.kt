package media.pixi.rx.firebase.auth.kit.ui.account

import dagger.android.support.DaggerFragment
import javax.inject.Inject

class AccountFragment @Inject constructor(): DaggerFragment(), AccountContract.View {

    var presenter: AccountContract.Presenter? = null
        @Inject set
}