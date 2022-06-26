package una.delta.frontenddelta.adapter

import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.RecyclerView
import una.delta.frontenddelta.databinding.ServiceItemBinding
import una.delta.frontenddelta.model.ServiceDetails

class ServiceAdapter : RecyclerView.Adapter<ServiceAdapter.ViewHolder>() {
    private var services = mutableListOf<ServiceDetails>()
    var tracker: SelectionTracker<Long>? = null

    init {
        setHasStableIds(true)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val service = services[position]

        tracker?.let {
            viewHolder.bind(service, it.isSelected(position.toLong()))
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = ServiceItemBinding.inflate(inflater, viewGroup, false)

        return ViewHolder(binding)
    }

    fun setServiceList(services: List<ServiceDetails>) {
        this.services = services as MutableList<ServiceDetails>
        notifyDataSetChanged()
    }

    override fun getItemCount() = services.size

    override fun getItemId(position: Int) = position.toLong()

    inner class ViewHolder(val binding: ServiceItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(value: ServiceDetails, isActivated: Boolean = false) {
            binding.tvServiceName.text = value.name
            binding.tvServiceDescription.text = value.description
            itemView.isActivated = isActivated
        }

        fun getItemDetails(): ItemDetailsLookup.ItemDetails<Long> =
            object : ItemDetailsLookup.ItemDetails<Long>() {
                override fun getPosition(): Int = bindingAdapterPosition
                override fun getSelectionKey(): Long? = itemId
            }
    }
}

class MyItemDetailsLookup(private val recyclerView: RecyclerView) : ItemDetailsLookup<Long>() {
    override fun getItemDetails(event: MotionEvent): ItemDetails<Long>? {
        val view = recyclerView.findChildViewUnder(event.x, event.y)
        if (view != null) {
            return (recyclerView.getChildViewHolder(view) as ServiceAdapter.ViewHolder)
                .getItemDetails()
        }
        return null
    }
}