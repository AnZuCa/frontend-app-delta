package una.delta.frontenddelta.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import una.delta.frontenddelta.adapter.MyRepairsAdapter
import una.delta.frontenddelta.databinding.FragmentMyRepairsBinding
import una.delta.frontenddelta.viewmodel.MyRepairsViewModel
import una.delta.frontenddelta.viewmodel.MyRepairsViewModelFactory


class MyRepairsFragment : Fragment() {

    private var _binding: FragmentMyRepairsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MyRepairsViewModel by activityViewModels{
        MyRepairsViewModelFactory()
    }
    private val adapter = MyRepairsAdapter()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyRepairsBinding.inflate(inflater, container, false)
        setUpRecyclerView()
        listenForEvents()
        loadAllRepair()

        return binding.root
    }

    private fun setUpRecyclerView() {
        val recyclerView: RecyclerView = binding.rvMyRepairs
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
    }

    private fun listenForEvents() {
        viewModel.repairList.observe(viewLifecycleOwner) {
            adapter.setRepairsList(it)
        }
    }

    private fun loadAllRepair() {
        viewModel.findRepairsByMechanic()
    }

}