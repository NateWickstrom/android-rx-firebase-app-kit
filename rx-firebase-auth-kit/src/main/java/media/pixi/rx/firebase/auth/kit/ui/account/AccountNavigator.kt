package media.pixi.rx.firebase.auth.kit.ui.account

import android.app.Activity
import android.content.Intent
import com.theartofdev.edmodo.cropper.CropImage
import media.pixi.rx.firebase.auth.kit.ui.passwordupdate.PasswordUpdateActivity
import media.pixi.rx.firebase.auth.kit.ui.signin.SignInActivity
import javax.inject.Inject
import android.app.Activity.RESULT_OK
import timber.log.Timber


class AccountNavigator @Inject constructor(): AccountContract.Navigator {

    override fun showUpdatePasswordScreen(activity: Activity) {
        val intent = Intent(activity, PasswordUpdateActivity::class.java)
        activity.startActivity(intent)
    }

    override fun showSignInScreen(activity: Activity) {
        val intent = Intent(activity, SignInActivity::class.java)
        // set the new task and clear flags
        intent.flags = (Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        activity.startActivity(intent)
    }

    override fun showImageFetcher(activity: Activity) {
        CropImage.activity()
            .start(activity)
    }

    override fun onShowImageFetcherResult(activity: Activity, requestCode: Int, resultCode: Int, data: Intent?): String? {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == RESULT_OK) {
                return result.uri.toString()
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                val error = result.error
                Timber.e(error)
            }
        }
        return null
    }
}