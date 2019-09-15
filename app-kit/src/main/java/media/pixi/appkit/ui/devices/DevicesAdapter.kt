package media.pixi.appkit.ui.devices

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.appkit__item_device.view.*
import media.pixi.appkit.R

class DevicesAdapter: RecyclerView.Adapter<DevicesAdapter.ViewHolder>() {

    var onClickListener: ((Int) -> Unit)? = null
    var onLongClickListener: ((Int) -> Boolean)? = null

    private val devices = mutableListOf<DeviceListItemModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view  = LayoutInflater.from(parent.context)
            .inflate(R.layout.appkit__item_device, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return devices.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItemAt(position)
        holder.bind(item)
        holder.itemView.setOnClickListener { onClickListener?.invoke(position) }
        holder.itemView.setOnLongClickListener { onLongClickListener?.invoke(position) ?: false}
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) TYPE_HEADER else TYPE_ITEM
    }

    fun setDevices(devices: List<DeviceListItemModel>) {
        this.devices.clear()
        this.devices.addAll(devices)
        notifyDataSetChanged()
    }

    private fun getItemAt(position: Int): DeviceListItemModel {
        return devices[position]
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun bind(item: DeviceListItemModel) = with(itemView) {
            title.text = item.title
            subtitle.text = item.subTitle
            checkmark.visibility = if (item.isSelected) View.VISIBLE else View.INVISIBLE
        }
    }

    companion object {
        private const val TYPE_HEADER = 0
        private const val TYPE_ITEM = 1
    }
}