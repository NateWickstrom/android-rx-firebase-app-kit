package media.pixi.appkit.data.storage

import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import io.reactivex.Single
import java.io.File

interface CloudStorageRepo {

    fun setUserProfileImage(file: File): Single<UploadTask.TaskSnapshot>

    fun getUserProfileImageReference(): StorageReference?
}