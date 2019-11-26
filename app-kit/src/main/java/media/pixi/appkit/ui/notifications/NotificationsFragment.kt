package media.pixi.appkit.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.appkit__fragment_list.view.*
import media.pixi.appkit.R
import media.pixi.appkit.domain.notifications.MyNotification
import media.pixi.appkit.ui.SwipeToDeleteCallback
import javax.inject.Inject

class NotificationsFragment @Inject constructor(): DaggerFragment(), NotificationsContract.View {

    override var hasResults: Boolean
        get() = false
        set(value) {
            viewOfLayout.empty_message.setText(R.string.no_notifications)
            viewOfLayout.empty_message.visibility = if (value) View.GONE else View.VISIBLE
        }

    override var error: String
        get() = ""
        set(value) {
            Toast.makeText(context, value, Toast.LENGTH_SHORT).show()
        }

    override var loading: Boolean
        get() = viewOfLayout.progress_bar.visibility == View.INVISIBLE
        set(value) {
            viewOfLayout.progress_bar.visibility = if (value) View.VISIBLE else View.INVISIBLE
        }

    lateinit var presenter: NotificationsContract.Presenter
        @Inject set

    private lateinit var viewOfLayout: View
    private lateinit var adapter: NotificationsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.appkit__fragment_list, container, false)
        adapter = NotificationsAdapter()

        view.list.layoutManager = LinearLayoutManager(context)
        view.list.adapter = adapter

        val swipeHandler = object : SwipeToDeleteCallback(context!!) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                adapter.removeAt(position)
                presenter.onDeleteNotification(position)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(view.list)

        adapter.onClickListener = { notification, position -> presenter.onItemClicked(activity!!, notification, position) }
        adapter.onLongClickListener = { notification, position -> presenter.onItemLongClicked(activity!!, notification, position) }
        adapter.onActionClickListener = { notification, position -> presenter.onAcceptFriendRequestClicked(notification, position) }

        viewOfLayout = view

        return viewOfLayout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.takeView(this)
    }

    override fun setResults(results: List<MyNotification>) {
        adapter.add(results)
    }

    override fun set(position: Int, notification: MyNotification) {
        adapter.set(position, notification)
    }

    override fun showMessage(message: String, onUndoClickListener: () -> Unit) {
        Snackbar.make(viewOfLayout, message, Snackbar.LENGTH_LONG)
            .setAction(R.string.action_undo) { onUndoClickListener.invoke() }
            .show()
    }
}