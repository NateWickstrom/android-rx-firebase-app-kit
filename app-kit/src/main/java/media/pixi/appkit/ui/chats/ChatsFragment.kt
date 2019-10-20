package media.pixi.appkit.ui.chats

import dagger.android.support.DaggerFragment
import javax.inject.Inject

class ChatsFragment @Inject constructor(): DaggerFragment(), ChatsContract.View {

    override var error: String
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}

    lateinit var presenter: ChatsContract.Presenter
        @Inject set

}