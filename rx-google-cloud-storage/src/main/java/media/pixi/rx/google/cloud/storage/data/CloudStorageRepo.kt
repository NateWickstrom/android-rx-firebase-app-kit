package media.pixi.rx.google.cloud.storage.data

import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import io.reactivex.Single
import java.io.File

interface CloudStorageRepo {

    fun setUserProfileImage(file: File): Single<UploadTask.TaskSnapshot>

    fun getUserProfileImageReference(): StorageReference
}