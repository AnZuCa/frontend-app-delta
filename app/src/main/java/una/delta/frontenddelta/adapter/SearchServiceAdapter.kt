package una.delta.frontenddelta.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import una.delta.frontenddelta.databinding.ServiceRowItemBinding
import una.delta.frontenddelta.model.ServiceDetails

class SearchServiceAdapter : RecyclerView.Adapter<ServiceRowViewHolder>() {

    var services = mutableListOf<ServiceDetails>()


    fun setServiceList(services: List<ServiceDetails>) {
        this.services = services.toMutableList()
        notifyDataSetChanged()
    }



    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ServiceRowViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = ServiceRowItemBinding.inflate(inflater, viewGroup, false)

        return ServiceRowViewHolder(binding)

    }

    override fun onBindViewHolder(viewHolder: ServiceRowViewHolder, position: Int) {
        val service = services[position]
        viewHolder.binding.textView5.text = service.name
        viewHolder.binding.textView6.text = service.description
    }

    override fun getItemCount() = services.size
}

class ServiceRowViewHolder(
    val binding: ServiceRowItemBinding
) : RecyclerView.ViewHolder(binding.root)
