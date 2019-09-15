package media.pixi.appkit.ui.devices

import android.app.Activity
import media.pixi.appkit.ui.BasePresenter
import media.pixi.appkit.ui.BaseView

interface DevicesContract {

    interface View: BaseView<Presenter> {
        var loading: Boolean

        fun setResults(results: List<DeviceListItemModel>)
        fun enabledSave(enable: Boolean)

    }

    interface Presenter: BasePresenter<View> {

        fun onListItemClicked(activity: Activity, position: Int)

        fun onSaveClicked()
    }

    interface Navigator {

    }
}