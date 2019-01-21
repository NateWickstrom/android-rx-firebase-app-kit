package media.pixi.rx.firebase.auth.kit.ui.signup

import media.pixi.rx.firebase.auth.kit.ui.BasePresenter
import media.pixi.rx.firebase.auth.kit.ui.BaseView

interface SignUpContract {

    interface View: BaseView<Presenter> {

    }

    interface Presenter: BasePresenter<View> {

    }
}