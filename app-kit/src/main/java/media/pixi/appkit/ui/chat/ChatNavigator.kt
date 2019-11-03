package media.pixi.appkit.ui.chat

import android.app.Activity
import media.pixi.appkit.ui.chatoptions.ChatOptionFragment
import javax.inject.Inject

class ChatNavigator @Inject constructor(): ChatContract.Navigator {

    override fun showImage(activity: Activity) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showOptions(activity: Activity) {
        val chatActivity = activity as ChatActivity
        val fragment = ChatOptionFragment.newInstance()
        fragment.presenter = chatActivity.optionsPresenter
        fragment.show(
            chatActivity.supportFragmentManager,
            OPTION_ID
        )
    }

    companion object {
        const val OPTION_ID = "options"
    }
}