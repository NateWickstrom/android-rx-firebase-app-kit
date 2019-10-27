package media.pixi.appkit.ui.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.appkit__fragment_list.view.*
import media.pixi.appkit.R
import media.pixi.appkit.data.audio.Recording
import media.pixi.appkit.ui.chat.textinput.TextInputListener
import media.pixi.appkit.ui.chat.textinput.TextInputView
import javax.inject.Inject

class ChatFragment @Inject constructor(): DaggerFragment(), ChatContract.View, TextInputListener {

    override var loading: Boolean
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}

    override var error: String
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}

    private var adapter: MessageAdapter? = null

    lateinit var presenter: ChatContract.Presenter
        @Inject set

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.appkit__fragment_chat, container, false)
        view.list.layoutManager = LinearLayoutManager(context)

        adapter = MessageAdapter()
        view.list.adapter = adapter

        val textInput = view.findViewById(R.id.view_message_text_input) as TextInputView
        textInput.setListener(this)

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
}