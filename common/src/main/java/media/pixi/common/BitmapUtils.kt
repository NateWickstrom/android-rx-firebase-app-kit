package media.pixi.common

import android.content.Context
import android.graphics.Bitmap
import timber.log.Timber
import android.graphics.BitmapFactory
import android.net.Uri
import java.io.*


object BitmapUtils {

    /**
     * Helper method to fetch a sampled version of a bitmap then scale the bitmap to the parameter's
     * width and height.
     *
     * @param ctx
     * @param uriString
     * @return
     * @throws IOException
     */
    @Throws(IOException::class)
    fun getBitmapFromUri(ctx: Context, uriString: String): Bitmap {

        try {

            val cr = ctx.contentResolver
            val uri = Uri.parse(uriString)
            val stream = BufferedInputStream(cr.openInputStream(uri))

            try {

                //get the bitmap safely, scale it and return it
                //fixme this is causing error because android cant properly load jpeg with this
                // http://stackoverflow.com/questions/2787015/skia-decoder-fails-to-decode-remote-stream
                //Bitmap bm = BitmapUtils.getSampledBitmap(fd, width, height);

                return BitmapFactory.decodeStream(stream)
            } finally {
                closeQuietly(stream)
            }
        } catch (e: OutOfMemoryError) {
            Timber.e(e.message, e)
            throw IOException("fail to load bitmap - Uri = $uriString", e)
        } catch (e: Exception) {
            Timber.e(e.message, e)
            throw IOException("fail to load bitmap - Uri = $uriString", e)
        }

    }

    @Throws(IOException::class)
    fun getScaledBitmapFromUri(ctx: Context, uriString: String, width: Int, height: Int): Bitmap {
        try {
            val bm = getBitmapFromUri(ctx, uriString)

            // return the bitmap if its smaller than the requested size
            if (bm.width <= width && bm.height <= height) return bm

            // scale down the bitmap
            return Bitmap.createScaledBitmap(bm, width, height, false)
        } catch (e: NullPointerException) {
            Timber.e(e.message, e)
            throw IOException("fail to load bitmap - Uri = $uriString", e)
        } catch (e: OutOfMemoryError) {
            Timber.e(e.message, e)
            throw IOException("fail to load bitmap - Uri = $uriString", e)
        }
    }

    fun saveFile(bmp: Bitmap, f: File) {
        var out: OutputStream? = null
        try {
            out = BufferedOutputStream(FileOutputStream(f))
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out)
            out.flush()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                out?.close()
            } catch (ignore: Throwable) {
            }
        }
    }

    private fun closeQuietly(stream: InputStream?) {
        if (stream == null) return
        try {
            stream.close()
        } catch (ignored: IOException) {
        }
    }
}