package una.delta.frontenddelta.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import una.delta.frontenddelta.databinding.VehicleRowItemBinding
import una.delta.frontenddelta.model.Vehicle

class SearchVehicleAdapter : RecyclerView.Adapter<SearchVehicleAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: VehicleRowItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    private var vehicles = mutableListOf<Vehicle>()

    fun setVehicleList(vehicles: List<Vehicle>) {
        this.vehicles = vehicles as MutableList<Vehicle>
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = VehicleRowItemBinding.inflate(inflater, viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val vehicle = vehicles[position]

        viewHolder.binding.tvPlateNumberDetail.text = vehicle.plateNumber
        viewHolder.binding.tvVinNumberDetail.text = vehicle.vinNumber
        viewHolder.binding.tvEngineNumberDetail.text = vehicle.engineNumber
        viewHolder.binding.tvBrandDetail.text = vehicle.vehicleBrand
        viewHolder.binding.tvVehicleTypeDetail.text = vehicle.vehicleType
        viewHolder.binding.tvEngineTypeDetail.text = vehicle.engineType
    }

    override fun getItemCount() = vehicles.size
}
