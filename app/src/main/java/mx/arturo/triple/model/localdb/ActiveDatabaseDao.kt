package mx.arturo.triple.model.localdb

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ActiveDatabaseDao {
    @Insert
    fun insert(active : ActiveRoom)

    //get all actives
    @Query("SELECT * FROM active_table ORDER BY id DESC")
    fun getAllActives() : LiveData<List<ActiveRoom>>

    @Query("DELETE FROM active_table")
    fun clear()

    @Query("SELECT * FROM active_table WHERE cadena = 'SORIANA'")
    fun filterSoriana() : LiveData<List<ActiveRoom>>

//filters
    @Query("SELECT * FROM active_table WHERE cadena = :cadena")
    fun filterCadena(cadena: String) : LiveData<List<ActiveRoom>>

    @Query("SELECT * FROM active_table WHERE sucursal = :sucursal")
    fun filterSucursal(sucursal: String) : LiveData<List<ActiveRoom>>

    @Query("SELECT * FROM active_table WHERE det_gsp = :gspNumber")
    fun filterGsp(gspNumber: Int) : LiveData<List<ActiveRoom>>




}