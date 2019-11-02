package media.pixi.appkit.ui.chat.options

import android.app.Activity
import android.app.AlertDialog
import io.reactivex.Completable
import media.pixi.appkit.R

class ChatOptionsView(private val listener: ChatOptionsOnClickListener): BaseChatOption.Action {

    private var dialog: AlertDialog? = null

    fun show(activity: Activity): Boolean {
        val builder = AlertDialog.Builder(activity)

        val options = chatOptions()

        val items = arrayOfNulls<String>(options.size)
        var i = 0

        for (option in options) {
            items[i++] = option.getTitle()
        }

        builder.setTitle(activity.getString(R.string.actions))
            .setItems(items) { dialogInterface, i1 ->
                listener.executeChatOption(options.get(i1))
                hide()
            }

        dialog = builder.show()

        return true
    }

    fun hide(): Boolean {
        if (dialog != null) {
            dialog?.dismiss()
            dialog = null
        }
        return false
    }

    private fun chatOptions(): List<ChatOption>  {
        val list = mutableListOf<ChatOption>()
        list.add(LocationChatOption("Share Location", R.drawable.ic_my_location_24px, null))
        return list
    }

    override fun execute(activity: Activity?, thread: Thread?): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}