package media.pixi.appkit.ui.chatcreator

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.appkit__item_person.view.*
import media.pixi.appkit.R
import media.pixi.appkit.data.profile.UserProfile
import media.pixi.appkit.utils.ImageUtils

class ContactsAdapter : RecyclerView.Adapter<ContactsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int {
        return 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun bind(item: UserProfile) = with(itemView) {
            ImageUtils.setUserImage(user_image, item.imageUrl)
            username.text = item.username
            name.text = itemView.context.getString(R.string.appkit__username, item.firstName, item.lastName)
        }
    }
}