package media.pixi.appkit.data.devices

import android.content.Context
import android.os.Build
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import durdinapps.rxfirebase2.RxFirestore
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single


class FirebaseDevicesProvider(context: Context): DevicesProvider {

    override var deviceId: String?
        get() = prefs.getString(DEVICE_ID, null)
        set(id) { prefs.edit().putString(DEVICE_ID, id).apply() }

    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)

    override fun registerDevice(): Completable {
        val map = mapOf(DEVICE_NAME to Build.MODEL, DEVICE_TYPE to ANDROID)
        return getDeviceDocSingle()
            .flatMapCompletable { RxFirestore.setDocument(it, map) }
    }

    override fun unregisterDevice(): Completable {
        return getDeviceDocSingle().flatMapCompletable { RxFirestore.deleteDocument(it) }
    }

    override fun unregisterDevice(devices: List<Device>): Completable {
        return getUserIdSingle()
            .map { id ->  getDeviceDocs(id, devices) }
            .flatMapCompletable { docs -> toCompletable(docs) }
    }

    override fun observerUserDeviceList(): Flowable<List<Device>> {
        return getUserIdSingle()
            .map { userId -> firestore.collection(DEVICES).document(userId).collection(DEVICES) }
            .flatMapMaybe { collection -> RxFirestore.getCollection(collection) }
            .map { snap -> toList(snap) }
            .toFlowable()
    }

    override fun observerRegisteredDeviceLimitHit(): Flowable<Boolean> {
        return observerUserDeviceList()
            .map { verifySize(it) }
    }

    private fun toList(snapshot: QuerySnapshot): List<Device> {
        return snapshot.documents.map {
            Device(
                name = it.get(DEVICE_NAME) as String? ?: "(model unknown)",
                instanceId = it.id,
                isCurrentDevice = deviceId.equals(it.id)) }
    }

    private fun verifySize(devices: List<Device>): Boolean {
        return devices.size >= DevicesProvider.MAX_DEVICE_COUNT
    }

    private fun getDeviceDocSingle(): Single<DocumentReference> {
        return Single.fromCallable { getDeviceDoc() }
    }

    private fun getDeviceDoc() : DocumentReference {
        val deviceId = deviceId ?: throw IllegalArgumentException("No device Id found.")
        val userId = auth.currentUser?.uid ?: throw IllegalArgumentException("No user Id found.")
        return firestore.collection(DEVICES).document(userId).collection(DEVICES).document(deviceId)
    }

    private fun toCompletable(docs: List<DocumentReference>): Completable {
        val completable = mutableListOf<Completable>()

        for (doc in docs) {
            completable.add(RxFirestore.deleteDocument(doc))
        }

        return Completable.concat(completable)
    }

    private fun getUserIdSingle(): Single<String> {
        return Single.fromCallable {
            auth.currentUser?.uid ?: throw IllegalArgumentException("No user Id found.")
        }
    }

    private fun getDeviceDocs(userId: String, devices: List<Device>): List<DocumentReference> {
        val docs = mutableListOf<DocumentReference>()

        for (device in devices) {
            docs.add(firestore.collection(DEVICES).document(userId).collection(DEVICES).document(device.instanceId))
        }

        return docs
    }

    companion object {
        // devices // userId // devices // instanceId // Device
        private const val ANDROID = "android"
        private const val DEVICES = "devices"
        private const val DEVICE_TYPE = "type"
        private const val DEVICE_NAME = "deviceName"

        private const val PREFS = "device_prefs"
        private const val DEVICE_ID = "device_id"

    }
}