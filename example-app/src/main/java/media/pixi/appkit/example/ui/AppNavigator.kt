package media.pixi.appkit.example.ui

import android.app.Activity
import android.content.Intent
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import media.pixi.appkit.example.ui.splash.SplashActivity
import media.pixi.appkit.ui.account.AccountContract
import media.pixi.appkit.ui.passwordupdate.PasswordUpdateActivity
import timber.log.Timber
import javax.inject.Inject

class AppNavigator @Inject constructor(): AccountContract.Navigator {

    override fun showUpdatePasswordScreen(activity: Activity) {
        val intent = Intent(activity, PasswordUpdateActivity::class.java)
        activity.startActivity(intent)
    }

    override fun showSignInScreen(activity: Activity) {
        val intent = Intent(activity, SplashActivity::class.java)
        // set the new task and clear flags
        intent.flags = (Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        activity.startActivity(intent)
    }

    override fun showImageFetcher(activity: Activity) {
        CropImage.activity()
            .setGuidelines(CropImageView.Guidelines.OFF)
            .setCropShape(CropImageView.CropShape.OVAL)
            .setFixAspectRatio(true)
            .start(activity)
    }

    override fun onShowImageFetcherResult(activity: Activity, requestCode: Int, resultCode: Int, data: Intent?): String? {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == Activity.RESULT_OK) {
                return result.uri.toString()
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                val error = result.error
                Timber.e(error)
            }
        }
        return null
    }
}