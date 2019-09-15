package media.pixi.appkit.ui.devices

import android.app.Activity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import media.pixi.appkit.data.devices.Device
import media.pixi.appkit.data.devices.DevicesProvider
import timber.log.Timber
import javax.inject.Inject

class DevicesPresenter @Inject constructor(private val devicesProvider: DevicesProvider,
                                           private val devicesNavigator: DevicesNavigator): DevicesContract.Presenter {

    private var view: DevicesContract.View? = null
    private var disposables = CompositeDisposable()

    private var devices = mutableListOf<Device>()
//        Device(name = "Pixel 2", instanceId = "1234", isCurrentDevice = true),
//        Device(name = "Pixel 3", instanceId = "2345", isCurrentDevice = false),
//        Device(name = "Pixel 4", instanceId = "3456", isCurrentDevice = false)
//    )

    private var listItems = mutableListOf<DeviceListItemModel>()

    override fun takeView(view: DevicesContract.View) {
        this.view = view
        view.loading = true

        disposables.add(devicesProvider.observerUserDeviceList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { onDeviceResult(it) },
                { onError(it) },
                { onNoResults() }
        ))
    }

    override fun dropView() {
        this.view = null
        disposables.clear()
    }

    override fun onListItemClicked(activity: Activity, position: Int) {
        listItems[position] = invertSelecting(listItems[position])
        view?.setResults(listItems)
        view?.enabledSave(canSave())
    }

    override fun onSaveClicked() {
        if (shouldLogOut()) {
            // TODO show logout warning
            return
        }

        val removeDevices = getRemovableDevices()

        disposables.add(devicesProvider.unregisterDevice(removeDevices).subscribe(
            { onSaveResult() },
            { onError(it) }
        ))
    }

    private fun onNoResults() {
        view?.loading = false
    }

    private fun onDeviceResult(list: List<Device>) {
        view?.loading = false

        devices.clear()
        devices.addAll(list)
        listItems.clear()
        listItems.addAll(list.map { map(it) })
        view?.setResults(listItems)
    }

    private fun onSaveResult() {
        view?.loading = false
        // go to home scr
    }

    private fun onError(error: Throwable) {
        view?.loading = false
        Timber.d(error.message)
    }

    private fun canSave(): Boolean {
        var count = 0
        listItems.forEach { if (it.isSelected) count++ }
        return count <= DevicesProvider.MAX_DEVICE_COUNT
    }

    private fun map(device: Device): DeviceListItemModel {
        return DeviceListItemModel(
            title = device.name,
            subTitle = if (device.isCurrentDevice) "<current device>" else "",
            isSelected = true,
            isAndroid = true
        )
    }

    private fun invertSelecting(item: DeviceListItemModel): DeviceListItemModel {
        return DeviceListItemModel(
            title = item.title,
            subTitle = item.subTitle,
            isSelected = item.isSelected.not(),
            isAndroid = item.isAndroid
        )
    }

    private fun shouldLogOut(): Boolean {
        for ((index, item) in listItems.withIndex()) {
            val device = devices[index]
            if (item.isSelected.not() && device.isCurrentDevice)
                return true
        }
        return false
    }

    private fun getRemovableDevices(): List<Device> {
        val remove = mutableListOf<Device>()
        for ((index, item) in listItems.withIndex()) {
            val device = devices[index]
            if (item.isSelected.not())
                remove.add(device)
        }
        return remove
    }
}