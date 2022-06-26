package una.delta.frontenddelta.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import una.delta.frontenddelta.databinding.RepairRowItemBinding
import una.delta.frontenddelta.model.Repair
import una.delta.frontenddelta.model.RepairResult


class MyRepairsAdapter : RecyclerView.Adapter<MyRepairsAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: RepairRowItemBinding) : RecyclerView.ViewHolder(binding.root)

    private var repairs = mutableListOf<RepairResult>()


    fun setRepairsList(repairs: List<RepairResult>) {
        this.repairs = repairs as MutableList<RepairResult>
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = RepairRowItemBinding.inflate(inflater, viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
         val repair = repairs[position]

        viewHolder.binding.tvMyRepairsId.text = repair.id.toString()
        viewHolder.binding.tvMyRepairsDescription.text = repair.description
        viewHolder.binding.tvMyRepairsMechanicName.text = repair.mechanic.firstName +" "+ repair.mechanic.lastName
        viewHolder.binding.tvMyRepairsClientName.text = repair.client.firstName +" "+ repair.client.lastName
        viewHolder.binding.tvMyRepairsVehicleBrand.text = repair.vehicle.vehicleBrand
        viewHolder.binding.tvMyRepairsStatus.text = repair.status.name
    }

    override fun getItemCount() = repairs.size
}
