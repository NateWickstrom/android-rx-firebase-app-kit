package media.pixi.rx.google.cloud.storage.data

import android.net.Uri
import java.io.File
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import durdinapps.rxfirebase2.RxFirebaseStorage
import io.reactivex.Single


class GoogleCloudStorageDataSource: CloudStorageRepo {

    var storage = FirebaseStorage.getInstance()

    override fun setUserProfileImage(file: File): Single<UploadTask.TaskSnapshot> {
        val uri = Uri.fromFile(file)
        return RxFirebaseStorage.putFile(storage.reference.child(USER_PROFILE), uri)
    }

    override fun getUserProfileImageReference(): StorageReference {
        return storage.reference.child(USER_PROFILE)
    }

    companion object {
        private const val USER_PROFILE = "user_profile"
    }
}