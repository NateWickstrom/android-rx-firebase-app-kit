package media.pixi.appkit.data.files

import android.content.Context
import android.graphics.Bitmap
import io.reactivex.Completable
import java.io.File

class LocalFileProvider(private val context: Context): FileProvider {

    override fun add(directoryName: String, fileName: String, content: File): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun add(directoryName: String, fileName: String, content: Bitmap): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun delete(directoryName: String, fileName: String): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun delete(directoryName: String): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun delete(): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        private const val ROOT_DIR = "cached_files"
    }
}