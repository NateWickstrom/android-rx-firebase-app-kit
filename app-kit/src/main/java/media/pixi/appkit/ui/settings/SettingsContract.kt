package media.pixi.appkit.ui.settings

import android.app.Activity
import media.pixi.appkit.ui.BasePresenter

interface SettingsContract {

    interface View {

    }

    interface Presenter: BasePresenter<View> {
        fun onAccountClicked(activity: Activity)
        fun onDevelopClicked(activity: Activity)
    }

    interface Navigator {
        fun showAccount(activity: Activity)
        fun showDevelop(activity: Activity)
    }
}