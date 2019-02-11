package media.pixi.appkit.utils

import android.graphics.Bitmap
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import media.pixi.appkit.R

object ImageUtils {

    fun setUserImage(view: ImageView, imageUrl: String) {
        val options = RequestOptions()
            .apply(RequestOptions.circleCropTransform())
            .placeholder(R.drawable.ic_default_profile_image)
            .error(R.drawable.ic_default_profile_image)
        Glide.with(view.context)
            .load(imageUrl)
            .apply(options)
            .into(view)
    }
}