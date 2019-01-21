package media.pixi.rx.firebase.auth.kit.ui.passwordupdate

import android.app.Activity
import media.pixi.rx.firebase.auth.kit.ui.BasePresenter
import media.pixi.rx.firebase.auth.kit.ui.BaseView

interface PasswordUpdateContract {

    interface View: BaseView<Presenter> {

    }

    interface Presenter: BasePresenter<View> {
        fun onUpdateClicked(activity: Activity)
    }

    interface Navigator {
        fun onExit(activity: Activity)
    }
}