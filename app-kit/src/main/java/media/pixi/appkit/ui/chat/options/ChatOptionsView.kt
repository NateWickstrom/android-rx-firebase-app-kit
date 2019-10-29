package media.pixi.appkit.ui.chat.options

import android.app.Activity
import android.app.AlertDialog
import media.pixi.appkit.R

class ChatOptionsView(private val listener: ChatOptionsOnClickListener) {

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

        return list
    }
}