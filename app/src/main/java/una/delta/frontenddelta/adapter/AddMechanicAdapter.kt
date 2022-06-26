package una.delta.frontenddelta.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import una.delta.frontenddelta.databinding.MechanicRowItemBinding
import una.delta.frontenddelta.model.UserResult

class AddMechanicAdapter : RecyclerView.Adapter<AddMechanicAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: MechanicRowItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.radioButton.setOnClickListener {
                onItemClick?.invoke(mechanics[bindingAdapterPosition])
                notifyDataSetChanged()
            }
        }
    }

    private var mechanics = mutableListOf<UserResult>()
    private var selectedPosition = -1
    var onItemClick: ((UserResult) -> Unit)? = null

    fun setMechanicList(mechanics: List<UserResult>) {
        this.mechanics = mechanics as MutableList<UserResult>
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = MechanicRowItemBinding.inflate(inflater, viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val mechanic = mechanics[position]

        viewHolder.binding.tvMechanicIdDetail.text = mechanic.idCard
        viewHolder.binding.tvMechanicNameDetail.text = "${mechanic.firstName} ${mechanic.lastName}"
        viewHolder.binding.radioButton.isChecked = (position == selectedPosition)

        viewHolder.binding.radioButton.setOnCheckedChangeListener { _, checked ->
            if (checked) selectedPosition = viewHolder.bindingAdapterPosition
        }
    }

    override fun getItemCount() = mechanics.size
}
