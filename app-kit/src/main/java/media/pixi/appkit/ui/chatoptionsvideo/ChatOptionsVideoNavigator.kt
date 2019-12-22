package media.pixi.appkit.ui.chatoptionsvideo

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.core.content.FileProvider
import java.io.File
import javax.inject.Inject


class ChatOptionsVideoNavigator @Inject constructor(): ChatOptionsVideoContract.Navigator {

    override fun showGallery(activity: Activity) {
        val intent = Intent()
        intent.type = "video/*"
        intent.action = Intent.ACTION_GET_CONTENT
        activity.startActivityForResult(Intent.createChooser(intent, "Select Video"),
            GALLERY_VIDEO_REQUEST
        )
    }

    override fun showCamera(activity: Activity) {
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
            Log.e("MainActivity", error.message, error)
        }
    }

    companion object {
        const val GALLERY_VIDEO_REQUEST = 901
        const val CAMERA_VIDEO_REQUEST = 902
        const val TEMP_CAMERA_VIDEO_FILE = "temp_camera_video"

        const val FILE_PROVIDER_AUTHORITY = "media.pixi.appkit.example.fileprovider"
    }
}