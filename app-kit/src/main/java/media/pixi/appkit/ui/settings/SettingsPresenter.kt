package media.pixi.appkit.ui.settings

import android.app.Activity
import javax.inject.Inject

class SettingsPresenter @Inject constructor(private val navigator: SettingsNavigator): SettingsContract.Presenter {

    override fun takeView(view: SettingsContract.View) {

    }

    override fun dropView() {

    }

    override fun onAccountClicked(activity: Activity) {
        navigator.showAccount(activity)
    }

    override fun onDevelopClicked(activity: Activity) {
        navigator.showDevelop(activity)
    }
}