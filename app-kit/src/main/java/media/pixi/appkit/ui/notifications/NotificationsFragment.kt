package media.pixi.appkit.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.appkit__fragment_list.view.*
import kotlinx.android.synthetic.main.appkit__fragment_list.view.progress_bar
import media.pixi.appkit.R
import media.pixi.appkit.domain.notifications.Notification
import javax.inject.Inject

class NotificationsFragment @Inject constructor(): DaggerFragment(), NotificationsContract.View {

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

        view.results.layoutManager = LinearLayoutManager(context)
        view.results.adapter = adapter

        viewOfLayout = view

        return viewOfLayout
    }

    override fun setResults(results: List<Notification>) {
        adapter.add(results)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.takeView(this)
    }
}