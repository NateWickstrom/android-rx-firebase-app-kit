package media.pixi.appkit.data.storage

import android.net.Uri
import com.google.firebase.storage.UploadTask
import io.reactivex.Maybe
import io.reactivex.Single
import java.io.File

interface CloudStorageRepo {

    fun setUserProfileImage(file: File): Single<UploadTask.TaskSnapshot>

    fun getUserProfileImageReference(): Maybe<Uri>

    fun addFile(id: String, file: File): Single<UploadTask.TaskSnapshot>

    fun getFile(id: String): Maybe<Uri>

}