package media.pixi.appkit.ui.chatcreator

import android.app.Activity
import android.os.Bundle
import android.view.*
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.appkit__fragment_create_chat.*
import kotlinx.android.synthetic.main.appkit__fragment_create_chat.view.*
import media.pixi.appkit.R
import media.pixi.appkit.data.profile.UserProfile
import media.pixi.appkit.utils.ImageUtils
import media.pixi.appkit.utils.TextChangeListner
import javax.inject.Inject


class ChatCreatorFragment @Inject constructor(): DaggerFragment(), ChatCreatorContract.View,
    TextChangeListner {

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
    private var adapter: ContactsAdapter? = null

    lateinit var presenter: ChatCreatorContract.Presenter
    private var chipGroup: ChipGroup? = null

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

        chipGroup = view.findViewById(R.id.chip_group)

        adapter = ContactsAdapter()
        adapter?.onClickListener = { presenter.onListItemClicked(activity as Activity, it) }

        view.list.adapter = adapter

        val searchBar = view.findViewById(R.id.search_bar) as EditText
        searchBar.addTextChangedListener(this)
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_create_chat) {
            presenter.onCreateChatClicked(activity!!)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun setContacts(results: List<UserProfile>) {
        adapter?.set(results)
    }

    override fun setSelectedContacts(results: Set<UserProfile>) {
        setChips(results)
    }

    override fun showNoResults(show: Boolean) {
        empty_message.setText(R.string.no_friends)
        empty_message.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        presenter.onTextChanged(s.toString())
    }

    private fun setChips(people: Collection<UserProfile>) {
        chipGroup?.removeAllViews()
        people
            //.sortedBy {  }
            .forEach { chipGroup?.addView(createChip(it)) }
    }

    private fun createChip(user: UserProfile): Chip {
        val inflater = LayoutInflater.from(context)
        val chip = inflater.inflate(R.layout.view_chip, chipGroup, false) as Chip
        chip.text = user.firstName
        chip.setOnCloseIconClickListener { presenter.onRemoveContactClicked(activity!!, user)}
        chip.setOnClickListener { presenter.onSelectedContactClicked(activity!!, user)}
        ImageUtils.setChipIcon(context!!, chip, user.imageUrl)
        return chip
    }

}