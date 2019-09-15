package media.pixi.appkit.ui.devices

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.appkit__fragment_devices.*
import kotlinx.android.synthetic.main.appkit__fragment_devices.view.*
import media.pixi.appkit.R
import javax.inject.Inject


class DevicesFragment @Inject constructor(): DaggerFragment(), DevicesContract.View {

    override var loading: Boolean
        get() = progress_bar.visibility == View.INVISIBLE
        set(value) { progress_bar.visibility = if (value) View.VISIBLE else View.INVISIBLE }

    override var error: String
        get() = ""
        set(value) {
            if (value.isNotBlank()) {
                Toast.makeText(context, error, Toast.LENGTH_LONG).show()
            }
        }

    lateinit var presenter: DevicesContract.Presenter

    private var adapter: DevicesAdapter? = null
    private var fab: FloatingActionButton? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.appkit__fragment_devices, container, false)
        view.devices.layoutManager = LinearLayoutManager(context)

        adapter = DevicesAdapter()
        adapter?.onClickListener = { presenter.onListItemClicked(activity as Activity, it) }
        view.devices.adapter = adapter
        fab = view.fab
        fab?.setOnClickListener { presenter.onSaveClicked() }

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

    override fun setResults(results: List<DeviceListItemModel>) {
        adapter?.let { it.setDevices(results) }
    }

    override fun enabledSave(enable: Boolean) {
        fab?.isEnabled = enable
    }
}