package media.pixi.appkit.ui.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.leinardi.android.speeddial.SpeedDialActionItem
import com.leinardi.android.speeddial.SpeedDialOverlayLayout
import com.leinardi.android.speeddial.SpeedDialView
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.appkit__fragment_list.view.*
import media.pixi.appkit.R
import media.pixi.appkit.data.audio.Recording
import media.pixi.appkit.domain.chats.Message
import media.pixi.appkit.ui.chat.actions.CopyMessageAction
import media.pixi.appkit.ui.chat.actions.DeleteMessageAction
import media.pixi.appkit.ui.chat.actions.ForwardMessageAction
import media.pixi.appkit.ui.chat.actions.MessageAction
import media.pixi.appkit.ui.chat.textinput.TextInputListener
import media.pixi.appkit.ui.chat.textinput.TextInputView
import media.pixi.appkit.utils.ActivityUtils
import java.util.ArrayList
import javax.inject.Inject

class ChatFragment @Inject constructor(): DaggerFragment(), ChatContract.View, TextInputListener,
    SpeedDialView.OnActionSelectedListener {

    override var loading: Boolean
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}

    override var error: String
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}

    private var adapter: MessageAdapter? = null
    private var speedDialView: SpeedDialView? = null

    lateinit var presenter: ChatContract.Presenter
        @Inject set

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.appkit__fragment_chat, container, false)
        view.list.layoutManager = LinearLayoutManager(context)

        adapter = MessageAdapter(presenter)
        view.list.adapter = adapter

        val textInput = view.findViewById(R.id.view_message_text_input) as TextInputView
        textInput.setListener(this)

        //val speedDialOverlay = view.findViewById(R.id.messageOptionsOverlay) as SpeedDialOverlayLayout
        speedDialView = view.findViewById(R.id.speed_dial_message_actions)
        speedDialView?.setOnActionSelectedListener(this)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.takeView(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.dropView()
        adapter = null
    }

    override fun setResults(results: List<MessageListItem>) {
        adapter?.set(results)
    }

    override fun showTextSpeedDial(messageListItem: MessageListItem) {
        ActivityUtils.hideKeyboard(activity!!)
        clearActions()
        addActions(getTextActions(messageListItem.isMe, messageListItem.message))
        speedDialView?.show()
    }

    override fun showImageSpeedDial(messageListItem: MessageListItem) {
        ActivityUtils.hideKeyboard(activity!!)

        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showLocationSpeedDial(messageListItem: MessageListItem) {
        ActivityUtils.hideKeyboard(activity!!)

        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onActionSelected(actionItem: SpeedDialActionItem?): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showOptions() {

    }

    override fun hideOptions() {

    }

    override fun onSendPressed(text: String) {

    }

    override fun startTyping() {

    }

    override fun sendAudio(recording: Recording) {

    }

    override fun stopTyping() {

    }

    override fun onKeyboardShow() {

    }

    override fun onKeyboardHide() {

    }

    private fun clearActions() {
        speedDialView?.let { view ->
            for (item in view.actionItems) {
                view.removeActionItem(item)
            }
            view.close(false)
        }

    }

    private fun addActions(actions: List<MessageAction>) {
        speedDialView?.let { view ->
            for (i in actions.indices) {
                val action = actions[i]
                view.addActionItem(
                    SpeedDialActionItem.Builder(i, action.iconResourceId)
                        .setLabel(action.titleResourceId)
                        .create()
                )
            }
        }

    }

    private fun getTextActions(isMe: Boolean, message: Message): List<MessageAction> {
        val actions = ArrayList<MessageAction>()

        if (isMe) {
            actions.add(DeleteMessageAction(message))
        }
        actions.add(CopyMessageAction(message))
        actions.add(ForwardMessageAction(message))

        return actions
    }
}