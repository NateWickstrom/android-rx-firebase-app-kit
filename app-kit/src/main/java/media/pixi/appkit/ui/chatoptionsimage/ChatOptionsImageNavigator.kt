package media.pixi.appkit.ui.chatoptionsimage

import android.app.Activity
import android.content.Intent
import android.os.Environment
import android.provider.MediaStore
import androidx.core.content.FileProvider
import media.pixi.appkit.ui.chat.ChatActivity
import timber.log.Timber
import java.io.File
import javax.inject.Inject


class ChatOptionsImageNavigator @Inject constructor(): ChatOptionsImageContract.Navigator {

    override fun showGallery(activity: Activity) {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        activity.startActivityForResult(Intent.createChooser(intent, "Select Picture"), GALLERY_IMAGE_REQUEST)
    }

    override fun showCamera(activity: Activity) {
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

    companion object {
        const val GALLERY_IMAGE_REQUEST = 801
        const val CAMERA_IMAGE_REQUEST = 802
        const val TEMP_CAMERA_IMAGE_FILE = "temp_camera_image"

        const val FILE_PROVIDER_AUTHORITY = "media.pixi.appkit.example.fileprovider"
    }
}