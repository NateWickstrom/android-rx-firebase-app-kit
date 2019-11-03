package media.pixi.appkit.ui.imageviewer

import media.pixi.appkit.ui.BasePresenter
import media.pixi.appkit.ui.BaseView

interface ImageViewerContract {

    interface View : BaseView<Presenter> {

    }

    interface Presenter : BasePresenter<View> {

    }

    interface Navigator {

    }
}