package una.delta.frontenddelta.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import una.delta.frontenddelta.adapter.SearchServiceAdapter
import una.delta.frontenddelta.databinding.FragmentSearchServiceBinding
import una.delta.frontenddelta.viewmodel.SearchServiceViewModel
import una.delta.frontenddelta.viewmodel.SearchServiceViewModelFactory

class SearchServiceFragment : Fragment() {

    private lateinit  var _binding: FragmentSearchServiceBinding
    private  val serviceViewModel: SearchServiceViewModel by activityViewModels(){
        SearchServiceViewModelFactory()
    }

    private val adapter = SearchServiceAdapter()

    //override fun onCreate(savedInstanceState: Bundle?) {
    //super.onCreate(savedInstanceState)

    //}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchServiceBinding.inflate(layoutInflater)
        _binding.rvSearchService.layoutManager = LinearLayoutManager(getContext())
        _binding.rvSearchService.adapter = adapter



        //serviceViewModel =
            //ViewModelProvider(this, SearchServiceViewModelFactory())
                //.get(SearchServiceViewModel::class.java)

        // Observer method to bind data of serviceList into Recycler View
        serviceViewModel.serviceList.observe(viewLifecycleOwner, Observer {
            adapter.setServiceList(it)
        })

        //_binding.svSearchService.setOnClickListener() {
            //serviceViewModel.filterServiceList(_binding.svSearchService.query.toString())
        //})

        _binding.svSearchService.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {

                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                serviceViewModel.filterServiceList(newText)
                return false
            }
        })





        // We need when the fragment is created
        serviceViewModel.findAllService()
        // Inflate the layout for this fragment
        return _binding.root
    }


}