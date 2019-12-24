package media.pixi.appkit.data.files

import android.graphics.Bitmap
import io.reactivex.Completable
import io.reactivex.Single
import java.io.File
import java.io.InputStream

interface FileProvider {

    fun add(directoryName: String, fileName: String, file: File): Single<String>

    fun add(directoryName: String, fileName: String, inputStream: InputStream): Single<String>

    fun add(directoryName: String, fileName: String, bitmap: Bitmap): Single<String>

    fun get(directoryName: String, fileName: String): File

    fun delete(directoryName: String, fileName: String): Completable

    fun delete(directoryName: String): Completable

    fun delete(): Completable
}
