package media.pixi.appkit.ui.chat

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import androidx.core.content.FileProvider
import media.pixi.appkit.ui.chat.options.ChatOptionsBottomSheetFragment
import media.pixi.appkit.ui.chat.options.ChatOptionsImagesBottomSheetFragment
import media.pixi.appkit.ui.chat.options.ChatOptionsVideosBottomSheetFragment
import media.pixi.appkit.ui.chatmembers.ChatMembersActivity
import timber.log.Timber
import java.io.File
import javax.inject.Inject

class ChatNavigator @Inject constructor() : ChatContract.Navigator,
    ChatOptionsBottomSheetFragment.PrimaryOptionOnClickListener,
    ChatOptionsImagesBottomSheetFragment.ChatOptionsForImagesOnClickListener,
    ChatOptionsVideosBottomSheetFragment.ChatOptionsOnVideoClickedListener {

    override fun showImage(activity: Activity) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showOptions(activity: Activity) {
        val chatActivity = activity as ChatActivity
        val fragment = ChatOptionsBottomSheetFragment.newInstance()
        fragment.listener = this
        fragment.show(
            chatActivity.supportFragmentManager,
            OPTION_ID
        )
    }

    override fun showChatMembers(activity: Activity, chatId: String) {
        ChatMembersActivity.launch(activity, chatId)
    }

    override fun showImageChooser(activity: Activity) {
        if (activity is ChatActivity) {
            val fragment = ChatOptionsImagesBottomSheetFragment.newInstance()
            fragment.listener = this
            fragment.show(activity.supportFragmentManager, IMAGE_ID)
        }
    }

    override fun showVideoChooser(activity: Activity) {
        if (activity is ChatActivity) {
            val fragment = ChatOptionsVideosBottomSheetFragment.newInstance()
            fragment.listener = this
            fragment.show(activity.supportFragmentManager, VIDEO_ID)
        }
    }

    override fun onGalleryImageClicked(activity: Activity) {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        activity.startActivityForResult(
            Intent.createChooser(intent, "Select Picture"),
            GALLERY_IMAGE_REQUEST
        )
    }

    override fun onCameraImageClicked(activity: Activity) {
        try {
            val storageDir = activity.getExternalFilesDir(Environment.DIRECTORY_DCIM)
            val file = File.createTempFile(TEMP_CAMERA_IMAGE_FILE, null, storageDir)
            if (activity is ChatActivity) {
                activity.imageFilePath = file.absolutePath
            }
            val uri = FileProvider.getUriForFile(
                activity,
                FILE_PROVIDER_AUTHORITY,
                file
            )
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
            activity.startActivityForResult(intent, CAMERA_IMAGE_REQUEST)
        } catch (error: Exception) {
            Timber.e(error)
        }
    }

    override fun onGalleryVideoClicked(activity: Activity) {
        val intent = Intent()
        intent.type = "video/*"
        intent.action = Intent.ACTION_GET_CONTENT
        activity.startActivityForResult(
            Intent.createChooser(intent, "Select Video"),
            GALLERY_VIDEO_REQUEST
        )
    }

    override fun onCameraVideoClicked(activity: Activity) {
        try {
            val storageDir = activity.getExternalFilesDir(Environment.DIRECTORY_DCIM)
            val file = File.createTempFile(TEMP_CAMERA_VIDEO_FILE, null, storageDir)
            val photoURI: Uri = FileProvider.getUriForFile(
                activity,
                FILE_PROVIDER_AUTHORITY,
                file
            )
            val intent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            activity.startActivityForResult(intent, CAMERA_VIDEO_REQUEST)
        } catch (error: Exception) {
            Timber.e(error)
        }
    }

    companion object {
        const val OPTION_ID = "options"
        const val IMAGE_ID = "image_chooser"
        const val VIDEO_ID = "video_chooser"

        const val GALLERY_IMAGE_REQUEST = 801
        const val CAMERA_IMAGE_REQUEST = 802
        const val TEMP_CAMERA_IMAGE_FILE = "temp_camera_image"

        const val GALLERY_VIDEO_REQUEST = 901
        const val CAMERA_VIDEO_REQUEST = 902
        const val TEMP_CAMERA_VIDEO_FILE = "temp_camera_video"

        const val FILE_PROVIDER_AUTHORITY = "media.pixi.appkit.example.fileprovider"
    }
}