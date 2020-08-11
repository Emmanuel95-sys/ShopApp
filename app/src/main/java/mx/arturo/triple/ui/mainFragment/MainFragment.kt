package mx.arturo.triple.ui.mainFragment


import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

import mx.arturo.triple.R
import mx.arturo.triple.databinding.MainFragmentBinding
import mx.arturo.triple.model.localdb.ActiveDatabase
import mx.arturo.triple.model.webservice.ActivesWebService
import mx.arturo.triple.ui.adapters.ActiveAdapter
import okhttp3.Dispatcher

class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding : MainFragmentBinding = DataBindingUtil.inflate(inflater,
        R.layout.main_fragment, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = ActiveDatabase.getInstance(application).activeDatabaseDao
        val viewModelFactory = MainViewModelFactory(dataSource, application)
        val mainViewModel =
            ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        binding.mainViewModelInLayout = mainViewModel

        //call webservice
        mainViewModel.callWebService()

        //make Adapter
        val adapter = ActiveAdapter()
        binding.recyclerView.adapter = adapter

        mainViewModel.activesFromRoom.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.activesData = it
            }
        })
        //clear functionality
        binding.clearData.setOnClickListener {
            mainViewModel.onClear()
        }
        //filters
        binding.cadenaFilter.setOnClickListener {
            var chain = binding.ETquery.text.toString()
            if (chain == ""){
                chain = "SORIANA"
            }
            mainViewModel.onChainFilter(chain)
        }

        binding.fullList.setOnClickListener {
            mainViewModel.callWebService()
        }

        binding.sucursalFilter.setOnClickListener {
            var sucursal = binding.ETquery.text.toString()
            if(sucursal == ""){
                sucursal = "AEROPUERTO"
            }
            mainViewModel.onSucursalFilter(sucursal)
        }

        binding.gspFilter.setOnClickListener {
            var gsp = binding.ETquery.text.toString()
            if(gsp == ""){
                gsp = "903849"
            }

            mainViewModel.ongspFilter(gsp)
        }

        binding.setLifecycleOwner(this)

        //setMenu
        setHasOptionsMenu(true)
        return binding.root

    }

    //like layouts we inflate menus
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.overflow_menu, menu)
        var item =  menu.findItem(R.id.search)
        var searchView = item.actionView as SearchView
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
               return false
            }

        })
    }


}
