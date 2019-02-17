package media.pixi.appkit.ui.passwordreset

import android.app.Activity
import media.pixi.appkit.ui.BasePresenter

interface PasswordResetContract {

    interface View {
        var message: String?
    }

    interface Presenter: BasePresenter<View> {
        fun onOpenEmailClicked(activity: Activity)
    }

    interface Navigator {
        fun showEmail(activity: Activity)
    }
}
