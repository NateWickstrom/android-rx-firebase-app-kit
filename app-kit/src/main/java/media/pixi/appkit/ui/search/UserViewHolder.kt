package media.pixi.appkit.ui.search

import android.text.TextUtils
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.algolia.instantsearch.helper.android.highlighting.toSpannedString
import kotlinx.android.synthetic.main.appkit__item_person.view.*
import media.pixi.appkit.R
import media.pixi.appkit.utils.ImageUtils

class UserViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    fun bind(item: User) = with(itemView) {
        ImageUtils.setUserImage(user_image, item.imageUrl ?: "")
        username.text = item.username

        val firstname = item.highlightedFirstName?.toSpannedString() ?: item.firstname
        val lastname = item.highlightedLastName?.toSpannedString() ?: item.lastname

        //name.text = itemView.context.getString(R.string.appkit__username, firstname, lastname)
        name.text = TextUtils.concat(firstname, " ", lastname)
    }
}