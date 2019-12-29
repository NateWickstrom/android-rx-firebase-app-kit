package media.pixi.appkit.data.storage

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import durdinapps.rxfirebase2.RxFirebaseStorage
import io.reactivex.Maybe
import io.reactivex.Single
import java.io.File
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

    override fun getUserProfileImageReference(): Maybe<Uri> {
        auth.currentUser?.uid?.let {
            return RxFirebaseStorage.getDownloadUrl(storage.reference.child(USER_PROFILE).child(it))
        }
        throw Exception("No user found!")
    }

    override fun addFile(id: String, file: File): Single<UploadTask.TaskSnapshot> {
        val uri = Uri.fromFile(file)
        val bucket = storage.reference.child(CHAT_DOCUMENTS).child(id)
        return RxFirebaseStorage.putFile(bucket, uri)
    }

    override fun getFile(id: String): Maybe<Uri> {
        return RxFirebaseStorage.getDownloadUrl(storage.reference.child(CHAT_DOCUMENTS).child(id))
    }

    companion object {
        private const val USER_PROFILE = "user_profile"
        private const val CHAT_DOCUMENTS = "chat_documents"
    }
}