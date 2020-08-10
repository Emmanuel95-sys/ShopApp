package mx.arturo.triple.model.localdb

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName ="active_table")
data class ActiveRoom (
    @PrimaryKey(autoGenerate = true)
    var id : Long = 0L,
    var activo : Int = 0,
    var cadena : String = "",
    @ColumnInfo(name = "det_gsp")
    var determinanteGSP : Int  = 0,
    var latitud : Double = 0.0,
    var longitud : Double = 0.0,
    var sucursal : String = ""
)

