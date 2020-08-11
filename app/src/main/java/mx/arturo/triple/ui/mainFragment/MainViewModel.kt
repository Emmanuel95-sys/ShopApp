package mx.arturo.triple.ui.mainFragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.*
import mx.arturo.triple.model.localdb.ActiveDatabaseDao
import mx.arturo.triple.model.localdb.ActiveRoom
import mx.arturo.triple.model.webservice.ActivesWebService
import mx.arturo.triple.model.webservice.response.ActivesModel

class MainViewModel (val database : ActiveDatabaseDao,
                     application: Application): AndroidViewModel(application) {
    private var viewModelJob = Job()
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
    //Define the scope of the co-routines
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    var activesFromRoom = database.getAllActives()

    //web service instance
    val webService = ActivesWebService()

    fun callWebService() {
        uiScope.launch {
            val response = webService.getActives().await()
            val actives = response.getConjuntotiendasUsuarioResult
            addActives(actives)
        }
    }

    fun addActives(actives : List<ActivesModel>){
        uiScope.launch {

            for(active in actives){
                var activeRoom = ActiveRoom()

                activeRoom.activo = active.activo
                activeRoom.cadena = active.cadena
                activeRoom.determinanteGSP = active.determinanteGSP
                activeRoom.latitud = active.latitud
                activeRoom.longitud = active.longitud
                activeRoom.sucursal = active.sucursal

                insert(activeRoom)
            }
        }
    }

    private suspend fun insert(newActive : ActiveRoom){
        withContext(Dispatchers.IO){
            database.insert(newActive)
//            var newActive = database.getAllActives()
//            Log.i("clearROOM", newActive.value.toString())
        }
    }

    //filters
    fun onClear(){
        uiScope.launch {
            clear()
        }
    }

    private suspend fun clear(){
        withContext(Dispatchers.IO){
            database.clear()
        }
    }

    //var filteredList = database.getAllActives()

    fun onChainFilter(chain : String){
        uiScope.launch {
            onClear()
            filterChainDB(chain)
        }
    }

    private suspend fun filterChainDB(chain: String ) {
        withContext(Dispatchers.IO){

            val response = webService.getActives().await()
            val allActives = response.getConjuntotiendasUsuarioResult
            var filteredList = mutableListOf<ActivesModel>()

            for (active in allActives){
                if(active.cadena == chain){
                    filteredList.add(active)
                }
            }
            addActives(filteredList)
        }
    }

    fun onSucursalFilter(sucursal: String){
        uiScope.launch {
            onClear()
            filterSucursal(sucursal)
        }
    }

    private suspend fun filterSucursal(sucursal: String) {
        withContext(Dispatchers.IO) {
            val response = webService.getActives().await()
            val allActives = response.getConjuntotiendasUsuarioResult
            var filteredList = mutableListOf<ActivesModel>()

            for (active in allActives) {
                if (active.sucursal == sucursal) {
                    filteredList.add(active)
                }
            }
            addActives(filteredList)
        }
    }

    fun ongspFilter(gsp: String){
        uiScope.launch {
            onClear()
            filtergsp(gsp.toInt())
        }
    }

    private suspend fun filtergsp(gsp: Int) {
        withContext(Dispatchers.IO) {
            val response = webService.getActives().await()
            val allActives = response.getConjuntotiendasUsuarioResult
            var filteredList = mutableListOf<ActivesModel>()

            for (active in allActives) {
                if (active.determinanteGSP == gsp) {
                    filteredList.add(active)
                }
            }
            addActives(filteredList)
        }
    }


}

