package media.pixi.appkit.ui.chat

import dagger.android.support.DaggerFragment
import javax.inject.Inject

class ChatFragment @Inject constructor(): DaggerFragment(), ChatContract.View {

    override var error: String
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}

    lateinit var presenter: ChatContract.Presenter
        @Inject set
}