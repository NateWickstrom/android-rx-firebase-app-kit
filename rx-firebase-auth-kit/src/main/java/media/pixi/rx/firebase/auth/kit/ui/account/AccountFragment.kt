package media.pixi.rx.firebase.auth.kit.ui.account

import dagger.android.support.DaggerFragment
import javax.inject.Inject

class AccountFragment @Inject constructor(): DaggerFragment(), AccountContract.View {

    @Inject var presenter: AccountContract.Presenter? = null

}