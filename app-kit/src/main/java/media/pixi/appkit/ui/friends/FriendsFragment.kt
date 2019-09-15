package media.pixi.appkit.ui.friends

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.android.support.DaggerFragment
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.appkit__fragment_friends.*
import kotlinx.android.synthetic.main.appkit__fragment_friends.view.*
import media.pixi.appkit.R
import media.pixi.appkit.data.profile.UserProfile
import javax.inject.Inject

class FriendsFragment @Inject constructor(): DaggerFragment(), FriendsContract.View {

    override var loading: Boolean
        get() = progress_bar.visibility == View.INVISIBLE
        set(value) { progress_bar.visibility = if (value) View.VISIBLE else View.INVISIBLE }

    private val bucket = CompositeDisposable()
    private var adapter: FriendsAdapter? = null

    lateinit var presenter: FriendsContract.Presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.appkit__fragment_friends, container, false)
        view.hits.layoutManager = LinearLayoutManager(context)

        adapter = FriendsAdapter()
        adapter?.onClickListener = { presenter.onListItemClicked(activity as Activity, it) }
        view.hits.adapter = adapter

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

    override fun onDestroy() {
        super.onDestroy()
        bucket.clear()
    }

    override fun setResults(results: List<UserProfile>) {
        adapter?.let { adapter ->
            adapter.addAll(results)
        }
    }
}