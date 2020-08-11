package mx.arturo.triple.ui.mainFragment


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
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

//        mainViewModel.filteredList.observe(viewLifecycleOwner, Observer {
//            it?.let {
//                adapter.activesData = it
//            }
//        })

        binding.setLifecycleOwner(this)
        return binding.root

    }

    //overflow menu to implement
//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        inflater.inflate(R.menu.overflow_menu, menu)
//        super.onCreateOptionsMenu(menu, inflater)
//
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return super.onOptionsItemSelected(item)
//
//    }
}
