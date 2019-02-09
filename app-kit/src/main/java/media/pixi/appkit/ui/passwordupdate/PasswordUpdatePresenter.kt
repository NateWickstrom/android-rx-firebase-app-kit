package media.pixi.appkit.ui.passwordupdate

import android.app.Activity
import javax.inject.Inject

class PasswordUpdatePresenter @Inject constructor(
    private var navigator: PasswordUpdateContract.Navigator): PasswordUpdateContract.Presenter {

    override fun takeView(view: PasswordUpdateContract.View) {

    }

    override fun dropView() {

    }

    override fun onUpdateClicked(activity: Activity) {
        navigator.onExit(activity)
    }
}