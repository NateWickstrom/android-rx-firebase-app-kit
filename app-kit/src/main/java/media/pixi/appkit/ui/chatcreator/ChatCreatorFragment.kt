package media.pixi.appkit.ui.chatcreator

import dagger.android.support.DaggerFragment
import javax.inject.Inject

class ChatCreatorFragment @Inject constructor(): DaggerFragment(), ChatCreatorContract.View {

    override var error: String
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}


}