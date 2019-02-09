package media.pixi.appkit.data.storage

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import java.io.File
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import durdinapps.rxfirebase2.RxFirebaseStorage
import io.reactivex.Single
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class GoogleCloudStorageDataSource @Inject constructor(): CloudStorageRepo {

    private var storage = FirebaseStorage.getInstance()
    private var auth = FirebaseAuth.getInstance()

    override fun setUserProfileImage(file: File): Single<UploadTask.TaskSnapshot> {
        auth.currentUser?.uid?.let {
            val uri = Uri.fromFile(file)
            val bucket = storage.reference.child(USER_PROFILE).child(it)
            return RxFirebaseStorage.putFile(bucket, uri)
        } ?: return Single.error(Exception("No user found!"))
    }

    override fun getUserProfileImageReference(): StorageReference? {
        auth.currentUser?.uid?.let {
            return storage.reference.child(USER_PROFILE).child(it)
        }
        return null
    }

    companion object {
        private const val USER_PROFILE = "user_profile"
    }
}