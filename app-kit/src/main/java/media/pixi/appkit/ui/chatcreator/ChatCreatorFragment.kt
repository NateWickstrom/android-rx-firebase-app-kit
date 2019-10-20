package media.pixi.appkit.ui.chatcreator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.android.support.DaggerFragment
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.appkit__fragment_list.*
import kotlinx.android.synthetic.main.appkit__fragment_list.view.*
import media.pixi.appkit.R
import media.pixi.appkit.data.profile.UserProfile
import javax.inject.Inject
import android.view.MenuInflater
import androidx.appcompat.app.AppCompatActivity

class ChatCreatorFragment @Inject constructor(): DaggerFragment(), ChatCreatorContract.View {

    override var canCreate: Boolean
        get() = createIsVisible
        set(value) {
            createIsVisible = value
            activity?.invalidateOptionsMenu()
        }

    override var error: String
        get() = ""
        set(value) {
            Toast.makeText(context, value, Toast.LENGTH_SHORT).show()
        }

    override var loading: Boolean
        get() = progress_bar.visibility == View.INVISIBLE
        set(value) { progress_bar.visibility = if (value) View.VISIBLE else View.INVISIBLE }

    private var createIsVisible = false
    private val bucket = CompositeDisposable()
    private var adapter: ContactsAdapter? = null

    lateinit var presenter: ChatCreatorContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.appkit__fragment_create_chat, container, false)
        view.list.layoutManager = LinearLayoutManager(context)

        activity?.let {
            if (it is AppCompatActivity) {
                it.setSupportActionBar(view.findViewById(R.id.toolbar))
                it.supportActionBar?.setDisplayHomeAsUpEnabled(true)
            }
        }

        adapter = ContactsAdapter()

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
        adapter = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.appkit__new_conversation_menu, menu)

        menu.findItem(R.id.menu_create_chat)!!.isVisible = createIsVisible

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun setResults(results: List<UserProfile>) {

    }
}