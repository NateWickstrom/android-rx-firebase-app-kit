package media.pixi.appkit.data.files

import android.content.Context
import android.graphics.Bitmap
import io.reactivex.Completable
import io.reactivex.Single
import java.io.File
import java.io.InputStream

class LocalFileProvider(private val context: Context): FileProvider {

    override fun add(directoryName: String, fileName: String, file: File): Single<String> {
        return Single.fromCallable {
            val output = createFile(directoryName, fileName)
            file.copyTo(output, true)
            output.absolutePath
        }
    }

    override fun add(directoryName: String, fileName: String, bitmap: Bitmap): Single<String> {
        return Single.fromCallable {
            val file = createFile(directoryName, fileName)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, file.outputStream())
            file.absolutePath
        }
    }

    override fun add(directoryName: String, fileName: String, inputStream: InputStream): Single<String> {
        return Single.fromCallable {
            val file = createFile(directoryName, fileName)
            file.copyInputStreamToFile(inputStream)
            file.absolutePath
        }
    }

    override fun get(directoryName: String, fileName: String): File {
        val dir = File(getFilesDir(), directoryName)
        return File(dir, fileName)
    }

    override fun delete(directoryName: String, fileName: String): Completable {
        return Completable.fromAction {
            File(getFilesDir(), directoryName).delete()
        }
    }

    override fun delete(directoryName: String): Completable {
        return Completable.fromAction {
            File(getFilesDir(), directoryName).deleteRecursively()
        }
    }

    override fun delete(): Completable {
        return Completable.fromAction {
            getFilesDir().deleteRecursively()
        }
    }

    private fun File.copyInputStreamToFile(inputStream: InputStream) {
        this.outputStream().use { fileOut ->
            inputStream.copyTo(fileOut)
        }
    }

    private fun createFile(directoryName: String, fileName: String): File {
        val dir = File(getFilesDir(), directoryName)
        val file = File(dir, fileName)
        if (file.exists().not()) {
            file.mkdirs()
        }
        return file
    }

    private fun getFilesDir(): File {
        return File(context.filesDir, ROOT_DIR)
    }

    companion object {
        private const val ROOT_DIR = "cached_files"
    }
}