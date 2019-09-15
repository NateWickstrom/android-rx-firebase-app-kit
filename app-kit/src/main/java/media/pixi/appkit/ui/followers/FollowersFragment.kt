package media.pixi.appkit.ui.followers

import dagger.android.support.DaggerFragment
import javax.inject.Inject

class FollowersFragment @Inject constructor(): DaggerFragment(), FollowersContract.View {

    override var error: String
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}

    lateinit var presenter: FollowersContract.Presenter

}