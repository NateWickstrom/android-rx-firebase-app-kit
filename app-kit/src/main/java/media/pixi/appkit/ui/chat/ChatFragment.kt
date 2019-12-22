package media.pixi.appkit.ui.chat

import android.os.Bundle
import android.view.*
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.leinardi.android.speeddial.SpeedDialActionItem
import com.leinardi.android.speeddial.SpeedDialView
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.appkit__fragment_list.*
import kotlinx.android.synthetic.main.appkit__fragment_list.view.*
import media.pixi.appkit.R
import media.pixi.appkit.data.audio.Recording
import media.pixi.appkit.domain.chats.models.Message
import media.pixi.appkit.domain.chats.models.MessageListItem
import media.pixi.appkit.domain.chats.models.MessageType
import media.pixi.appkit.ui.chat.actions.*
import media.pixi.appkit.ui.chat.textinput.TextInputListener
import media.pixi.appkit.ui.chat.textinput.TextInputView
import media.pixi.appkit.ui.imageviewer.ImageViewerActivity
import media.pixi.appkit.utils.ActivityUtils
import java.util.*
import javax.inject.Inject

class ChatFragment @Inject constructor(): DaggerFragment(), ChatContract.View, TextInputListener,
    SpeedDialView.OnActionSelectedListener, SpeedDialView.OnChangeListener, MessageAdapter.OnMessageListItemClicked {

    override var loading: Boolean
        get() = progress_bar.visibility == View.INVISIBLE
        set(value) { progress_bar.visibility = if (value) View.VISIBLE else View.INVISIBLE }

    override var error: String
        get() = ""
        set(value) {}

    override var canSend: Boolean
        get() = isSendEnabled
        set(value) {
            isSendEnabled = value
        }

    override var title: String
        get() = activity?.title.toString()
        set(value) {
            activity?.title = value
        }

    private var isSendEnabled = false
    private var adapter: MessageAdapter? = null
    private var speedDialView: SpeedDialView? = null
    private var textInputView: TextInputView? = null
    private var listView: RecyclerView? = null
    private var attachment: ImageView? = null

    lateinit var presenter: ChatContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.appkit__fragment_chat, container, false)

        val swipeRefreshLayout = view.findViewById<SwipeRefreshLayout>(R.id.layout_swipe_to_refresh)
        swipeRefreshLayout.setRefreshing(false)
        swipeRefreshLayout.setEnabled(false)

        listView = view.list

        val layoutManager = LinearLayoutManager(context)
        layoutManager.stackFromEnd = true
        view.list.layoutManager = layoutManager

        adapter = MessageAdapter(this)
        view.list.adapter = adapter
        view.list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val first = layoutManager.findFirstVisibleItemPosition()
                val last = layoutManager.findLastVisibleItemPosition()
                if (first != RecyclerView.NO_POSITION && last != RecyclerView.NO_POSITION) {
                    presenter.onItemsViewed(first, last)
                }
            }
        })

        textInputView = view.findViewById(R.id.view_message_text_input) as TextInputView
        textInputView?.setListener(this)

        speedDialView = view.findViewById(R.id.speed_dial_message_actions)
        speedDialView?.setOnActionSelectedListener(this)
        speedDialView?.setOnChangeListener(this)

        attachment = view.findViewById(R.id.attachment)

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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.appkit__chat_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_people -> {
                presenter.onShowChatMembersClicked(activity!!)
                return true
            }
        }

        return false
    }

    override fun showImageAttachment(path: String) {
        textInputView?.setImageAttachment(path)
    }

    override fun showVideoAttachment(path: String) {
        textInputView?.setVideoAttachment(path)
    }

    override fun scrollToEnd() {
        adapter?.items?.size?.let {
            listView?.smoothScrollToPosition(it)
        }
    }

    override fun setResults(results: List<MessageListItem>) {
        adapter?.set(results)
    }

    override fun showTextSpeedDial(messageListItem: MessageListItem) {
        ActivityUtils.hideKeyboard(activity!!)
        speedDialView?.close(false)
        ActionUtils.setActions(speedDialView!!, getTextActions(messageListItem.isMe, messageListItem.message))
        speedDialView?.visibility = View.VISIBLE
        speedDialView?.open()
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
//        when (actionItem!!.id) {
//            MessageAction.Type.Copy.ordinal ->
//            MessageAction.Type.Delete.ordinal ->
//            MessageAction.Type.Forward.ordinal ->
//        }

        return true
    }

    override fun onMessageListItemClicked(position: Int, item: MessageListItem) {
        when (item.message.type) {
            MessageType.IMAGE -> {
                ImageViewerActivity.launch(activity!!, item.message.message)
            }
        }
    }

    override fun onMainActionSelected(): Boolean {
        return false
    }

    override fun onToggleChanged(isOpen: Boolean) {
        if (isOpen.not()) {
            speedDialView?.visibility = View.INVISIBLE
        }
    }

    override fun showOptions() {
        presenter.onOptionsClicked(activity!!)
    }

    override fun hideOptions() {

    }

    override fun onSendPressed(text: String) {
        //isSendEnabled
        ActivityUtils.hideKeyboard(activity!!)

        textInputView?.clearText()

        presenter.send(text)

//        val cnt = adapter?.itemCount ?: 0
//        if (cnt > 0) {
//            listView?.sm(cnt  - 1)
//        }
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