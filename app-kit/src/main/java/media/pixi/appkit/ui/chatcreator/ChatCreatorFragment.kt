package media.pixi.appkit.ui.chatcreator

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.appkit__fragment_list.*
import kotlinx.android.synthetic.main.appkit__fragment_list.view.*
import media.pixi.appkit.R
import media.pixi.appkit.data.profile.UserProfile
import javax.inject.Inject
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import android.view.LayoutInflater
import media.pixi.appkit.utils.ImageUtils


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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_create_chat) {
            presenter.onStartChatClicked(activity!!)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun setContacts(results: List<UserProfile>) {
        adapter?.set(results)
    }

    override fun setSelectedContacts(results: Set<UserProfile>) {
        //adapter?.setSelectedContacts(results)
        setChips(results)
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
        ImageUtils.setChipIcon(context!!, chip, user.imageUrl)
        return chip
    }

}