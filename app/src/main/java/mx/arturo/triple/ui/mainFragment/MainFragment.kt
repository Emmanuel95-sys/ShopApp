package mx.arturo.triple.ui.mainFragment


import android.os.Bundle
import android.view.*

import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import mx.arturo.triple.R
import mx.arturo.triple.databinding.MainFragmentBinding
import mx.arturo.triple.model.localdb.ActiveDatabase
import mx.arturo.triple.model.localdb.ActiveRoom
import mx.arturo.triple.ui.adapters.ActiveAdapter

class MainFragment : Fragment() {
    lateinit var adapter : ActiveAdapter
    lateinit var mainViewModel : MainViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding : MainFragmentBinding = DataBindingUtil.inflate(inflater,
        R.layout.main_fragment, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = ActiveDatabase.getInstance(application).activeDatabaseDao
        val viewModelFactory = MainViewModelFactory(dataSource, application)
        mainViewModel =
            ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        binding.mainViewModelInLayout = mainViewModel
        //make Adapter
        //val adapter = ActiveAdapter()
        //list
        mainViewModel.activesFromRoom.observe(viewLifecycleOwner, Observer {
            it?.let {
                //capture the list
                adapter = ActiveAdapter()
                adapter.activeListAll = it

                binding.recyclerView.adapter = adapter
            }
        })
        //
        binding.lifecycleOwner = this
        //setMenu
        setHasOptionsMenu(true)
        return binding.root

    }

    fun clearData(){
        //clear functionality
        mainViewModel.onClear()
    }

    fun callWebService(){
        mainViewModel.callWebService()
    }

    //like layouts we inflate menus
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.overflow_menu, menu)

        var item =  menu.findItem(R.id.search)
        var searchView = item.actionView as SearchView

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                adapter.getFilter().filter(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.getFilter().filter(newText)
               return false
            }

        })
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
//            R.id.clear_data -> clearData()
            R.id.call_web_service -> callWebService()
        }
        return true
    }
}
