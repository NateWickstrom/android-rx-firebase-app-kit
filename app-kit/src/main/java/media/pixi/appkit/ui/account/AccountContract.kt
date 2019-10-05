package media.pixi.appkit.ui.account

import android.app.Activity
import android.content.Intent
import media.pixi.appkit.ui.BasePresenter
import media.pixi.appkit.ui.BaseView

interface AccountContract {

    interface View: BaseView<Presenter> {
        var userImageUrl: String
        var username: String
        var firstName: String
        var lastName: String
        var email: String

        var loading: Boolean
    }

    interface Presenter: BasePresenter<View> {
        fun onUserImageClicked(activity: Activity)
        fun onSaveClicked(activity: Activity)
        fun onSignOutClicked(activity: Activity)
        fun onResetClicked(activity: Activity)
        fun onUpdatePasswordClicked(activity: Activity)
        fun onVerifyEmailClicked(activity: Activity)
    }

    interface Navigator {
        fun showUpdatePasswordScreen(activity: Activity)
        fun showSignInScreen(activity: Activity)
        fun showImageFetcher(activity: Activity)
        fun onShowImageFetcherResult(activity: Activity, requestCode: Int, resultCode: Int, data: Intent?): String?
    }
}