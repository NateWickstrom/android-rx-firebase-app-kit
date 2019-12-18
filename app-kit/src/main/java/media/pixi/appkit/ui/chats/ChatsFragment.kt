package media.pixi.appkit.ui.chats

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.appkit__fragment_list.*
import kotlinx.android.synthetic.main.appkit__fragment_list.view.*
import media.pixi.appkit.R
import media.pixi.appkit.data.chats.ChatEntity
import media.pixi.appkit.domain.chats.ChatListItemsGetter
import javax.inject.Inject

class ChatsFragment @Inject constructor(): DaggerFragment(), ChatsContract.View {

    override var error: String
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}

    override var loading: Boolean
        get() = progress_bar.visibility == View.INVISIBLE
        set(value) { progress_bar.visibility = if (value) View.VISIBLE else View.INVISIBLE }

    lateinit var presenter: ChatsContract.Presenter
    lateinit var getChats: ChatListItemsGetter
        @Inject set

    private var adapter: ChatAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.appkit__fragment_list, container, false)
        view.list.layoutManager = LinearLayoutManager(context)

        adapter = ChatAdapter(getChats)
        adapter?.onClickListener = { presenter.onListItemClicked(activity as Activity, it) }
        view.list.adapter = adapter

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.takeView(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.dropView()
        adapter?.unbind()
        adapter = null
    }

    override fun setResults(results: List<ChatEntity>) {
        adapter?.set(results)
    }
}