package media.pixi.appkit.ui.chatoptionsimage

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import androidx.core.content.FileProvider
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
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val storageDir: File = activity.getExternalFilesDir(Environment.DIRECTORY_DCIM)
        val file = File.createTempFile(
            TEMP_CAMERA_IMAGE_FILE,
            null,
            storageDir
        )
        val photoURI: Uri = FileProvider.getUriForFile(
            activity,
            "media.pixi.appkit.example.fileprovider",
            file
        )
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
        activity.startActivityForResult(intent, CAMERA_IMAGE_REQUEST)
    }

    companion object {
        const val GALLERY_IMAGE_REQUEST = 801
        const val CAMERA_IMAGE_REQUEST = 802
        const val TEMP_CAMERA_IMAGE_FILE = "temp_camera_image"

        fun tempFilePath(context: Context): String {
            val storageDir: File = context.getExternalFilesDir(Environment.DIRECTORY_DCIM)
            return File(storageDir, "$TEMP_CAMERA_IMAGE_FILE.tmp").absolutePath
        }
    }
}