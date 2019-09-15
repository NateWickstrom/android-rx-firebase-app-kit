package media.pixi.appkit.data.devices

import io.reactivex.Completable
import io.reactivex.Flowable

interface DevicesProvider {
    var deviceId: String?

    fun registerDevice(): Completable

    fun unregisterDevice(): Completable

    fun unregisterDevice(devices: List<Device>): Completable

    fun observerUserDeviceList(): Flowable<List<Device>>

    fun observerRegisteredDeviceLimitHit(): Flowable<Boolean>

    companion object {
        const val MAX_DEVICE_COUNT = 2
    }
}