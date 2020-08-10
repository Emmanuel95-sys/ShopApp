package mx.arturo.triple.ui.mainFragment

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import mx.arturo.triple.model.localdb.ActiveDatabaseDao
import java.lang.IllegalArgumentException

class MainViewModelFactory (
    private val dataSource: ActiveDatabaseDao,
    private val application: Application
): ViewModelProvider.Factory{
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}