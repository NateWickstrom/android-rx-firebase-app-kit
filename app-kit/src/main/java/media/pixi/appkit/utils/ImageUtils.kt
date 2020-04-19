package media.pixi.appkit.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.chip.Chip
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

    fun setChipIcon(context: Context, chip: Chip, imageUrl: String) {
        val options = RequestOptions()
            .apply(RequestOptions.circleCropTransform())
            .placeholder(R.drawable.ic_default_profile_image)
            .error(R.drawable.ic_default_profile_image)
        Glide.with(context)
            .load(imageUrl)
            .apply(options)
            .listener(ChipRequestListener(chip))
            .preload()

    }

    fun setAttachmentThumbnail(view: ImageView, imageUrl: String) {
        val options = RequestOptions()
            .transform(RoundedCorners(8))
            .placeholder(R.drawable.icn_200_image_message_placeholder)
            .error(R.drawable.icn_200_image_message_error)
        Glide.with(view.context)
            .load(imageUrl)
            .apply(options)
            .into(view)
    }
}