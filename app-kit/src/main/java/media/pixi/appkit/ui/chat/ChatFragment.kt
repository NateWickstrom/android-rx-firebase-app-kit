package media.pixi.appkit.ui.chat

import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.textfield.TextInputEditText
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.appkit__fragment_list.*
import kotlinx.android.synthetic.main.appkit__fragment_list.view.*
import media.pixi.appkit.R
import media.pixi.appkit.domain.chats.models.Message
import media.pixi.appkit.domain.chats.models.MessageAttachment
import media.pixi.appkit.domain.chats.models.MessageListItem
import media.pixi.appkit.domain.chats.models.MessageType
import media.pixi.appkit.ui.chat.actions.CopyMessageAction
import media.pixi.appkit.ui.chat.actions.DeleteMessageAction
import media.pixi.appkit.ui.chat.actions.ForwardMessageAction
import media.pixi.appkit.ui.chat.actions.MessageAction
import media.pixi.appkit.ui.chat.attachments.AttachmentAdapter
import media.pixi.appkit.ui.imageviewer.ImageViewerActivity
import media.pixi.appkit.utils.ActivityUtils
import java.util.*
import javax.inject.Inject

class ChatFragment @Inject constructor(): DaggerFragment(), ChatContract.View, TextView.OnEditorActionListener,
    MessageAdapter.OnMessageListItemClicked {

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

    override var text: String
        get() = etMessage?.text.toString()
        set(value) {
            etMessage?.setText(value)
        }

    private var isSendEnabled = false

    private var messageAdapter: MessageAdapter? = null
    private var messagesRecyclerView: RecyclerView? = null

    private var attachmentsAdapter: AttachmentAdapter? = null
    private var attachmentsRecyclerView: RecyclerView? = null

    private var btnSend: ImageButton? = null
    private var btnOptions: ImageButton? = null
    private var etMessage: TextInputEditText? = null

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

        messagesRecyclerView = view.list

        val layoutManager = LinearLayoutManager(context)
        layoutManager.stackFromEnd = true
        view.list.layoutManager = layoutManager

        messageAdapter = MessageAdapter(this)
        view.list.adapter = messageAdapter
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

        btnSend = view.findViewById<ImageButton>(R.id.button_send)
        btnSend?.setOnClickListener {
                _ -> onSendPressed(etMessage?.text!!.toString())
        }

        btnOptions = view.findViewById<ImageButton>(R.id.button_options)
        btnOptions?.setOnClickListener { _ -> showOptions() }

        etMessage = view.findViewById<TextInputEditText>(R.id.text_input_message)
        etMessage?.setOnEditorActionListener(this)
        etMessage?.setOnKeyListener { v, keyCode, event -> false }
        etMessage?.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_SENTENCES)
        //etMessage?.setOnFocusChangeListener { view, focus -> }

        etMessage?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun afterTextChanged(editable: Editable) {}
        })

        attachmentsRecyclerView = view.findViewById<RecyclerView>(R.id.attachments)
        attachmentsRecyclerView?.setLayoutManager(
            LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        )

        attachmentsAdapter = AttachmentAdapter(
            { position, attachment -> presenter.onAttachmentClicked(position, attachment) },
            { position, attachment -> presenter.onAttachmentDeleteClicked(position, attachment) }
        )
        attachmentsRecyclerView?.adapter = attachmentsAdapter

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.takeView(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        val text = etMessage?.text.toString()
        val attachments =  attachmentsAdapter?.get() ?: emptyList()
        presenter.saveDraft(text, attachments)
        presenter.dropView()
        messageAdapter = null
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

    override fun addAttachment(attachment: MessageAttachment) {
        attachmentsRecyclerView?.visibility = View.VISIBLE
        attachmentsAdapter?.add(attachment)
    }

    override fun addAttachments(attachments: List<MessageAttachment>) {
        attachmentsRecyclerView?.visibility = View.VISIBLE
        attachmentsAdapter?.add(attachments)
    }

    override fun scrollToEnd() {
        messageAdapter?.items?.size?.let {
            messagesRecyclerView?.smoothScrollToPosition(it)
        }
    }

    override fun setResults(results: List<MessageListItem>) {
        messageAdapter?.set(results)
    }

    override fun showTextSpeedDial(messageListItem: MessageListItem) {
        ActivityUtils.hideKeyboard(activity!!)

    }

    override fun showImageSpeedDial(messageListItem: MessageListItem) {
        ActivityUtils.hideKeyboard(activity!!)

        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showLocationSpeedDial(messageListItem: MessageListItem) {
        ActivityUtils.hideKeyboard(activity!!)

        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onMessageListItemClicked(position: Int, item: MessageListItem) {
        when (item.message.type) {
            MessageType.IMAGE -> {
                ImageViewerActivity.launch(activity!!, item.message.message)
            }
        }
    }

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        if (actionId == EditorInfo.IME_ACTION_SEND) {
            onSendPressed(etMessage?.text!!.toString())
        }

        return false
    }

    private fun showOptions() {
        presenter.onOptionsClicked(activity!!)
    }

    private fun onSendPressed(text: String) {
        //isSendEnabled?
        ActivityUtils.hideKeyboard(activity!!)

        etMessage?.setText("")

        presenter.send(text, attachmentsAdapter?.get()!!)
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