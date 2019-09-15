package media.pixi.appkit.ui.followers

import media.pixi.appkit.ui.BasePresenter
import media.pixi.appkit.ui.BaseView

interface FollowersContract {

    interface View: BaseView<Presenter> {

    }

    interface Presenter: BasePresenter<View> {

    }

    interface Navigator {

    }
}