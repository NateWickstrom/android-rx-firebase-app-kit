package media.pixi.appkit.ui.chatoptions

import android.app.Activity
import javax.inject.Inject
import android.provider.MediaStore
import android.content.Intent


class ChatOptionsNavigator @Inject constructor(): ChatOptionsContract.Navigator {

    override fun showGallery(activity: Activity) {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        activity.startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE)
    }

    override fun showCamera(activity: Activity) {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//        imageUri = getImageUri()
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        activity.startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
    }

    companion object {
        const val REQUEST_IMAGE_CAPTURE = 99
        const val PICK_IMAGE = 98
    }
}