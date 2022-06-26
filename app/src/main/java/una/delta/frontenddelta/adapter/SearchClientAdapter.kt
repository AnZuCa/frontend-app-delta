package una.delta.frontenddelta.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import una.delta.frontenddelta.databinding.ClientRowItemBinding
import una.delta.frontenddelta.model.UserResult
import una.delta.frontenddelta.model.Vehicle


class SearchClientAdapter :
    RecyclerView.Adapter<SearchClientAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ClientRowItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    private var clients = mutableListOf<UserResult>()

    fun setClientList(clients: List<UserResult>) {
        this.clients = clients as MutableList<UserResult>
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = ClientRowItemBinding.inflate(inflater, viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val client = clients[position]

        viewHolder.binding.tvSearchClientId.text = client.idCard.toString()
        viewHolder.binding.tvSearchClientName.text = client.firstName  +" " +client.lastName
        viewHolder.binding.tvSearchClientEmail.text = client.email
        viewHolder.binding.tvSearchClientAddress.text = client.address
        viewHolder.binding.tvSearchClientMobileNumber.text = client.mobileNumber

    }

    override fun getItemCount() = clients.size
}
