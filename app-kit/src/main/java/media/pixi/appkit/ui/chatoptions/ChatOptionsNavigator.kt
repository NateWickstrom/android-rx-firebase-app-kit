package media.pixi.appkit.ui.chatoptions

import android.app.Activity
import media.pixi.appkit.ui.chat.ChatActivity
import media.pixi.appkit.ui.chatoptionsimage.ChatOptionsImageFragment
import media.pixi.appkit.ui.chatoptionsvideo.ChatOptionsVideoFragment
import javax.inject.Inject


class ChatOptionsNavigator @Inject constructor(): ChatOptionsContract.Navigator {

    override fun showImageChooser(activity: Activity) {
        if (activity is ChatActivity) {
            val fragment = ChatOptionsImageFragment.newInstance()
            fragment.presenter = activity.optionsImagePresenter
            fragment.show(activity.supportFragmentManager, IMAGE_ID)
        }
    }

    override fun showVideoChosser(activity: Activity) {
        if (activity is ChatActivity) {
            val fragment = ChatOptionsVideoFragment.newInstance()
            fragment.presenter = activity.optionsVideoPresenter
            fragment.show(activity.supportFragmentManager, VIDEO_ID)
        }
    }

    companion object {
        const val IMAGE_ID = "image_chooser"
        const val VIDEO_ID = "video_chooser"
    }
}