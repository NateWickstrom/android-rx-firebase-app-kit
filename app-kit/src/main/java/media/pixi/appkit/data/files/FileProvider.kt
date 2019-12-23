package media.pixi.appkit.data.files

import android.graphics.Bitmap
import io.reactivex.Completable
import java.io.File

interface FileProvider {

    fun add(directoryName: String, fileName: String, content: File): Completable

    fun add(directoryName: String, fileName: String, content: Bitmap): Completable

    fun delete(directoryName: String, fileName: String): Completable

    fun delete(directoryName: String): Completable

    fun delete(): Completable
}
