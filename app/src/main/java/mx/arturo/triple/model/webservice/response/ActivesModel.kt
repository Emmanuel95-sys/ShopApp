package mx.arturo.triple.model.webservice.response


import com.google.gson.annotations.SerializedName

data class ActivesModel(

    @SerializedName("Activo")
    var activo: Int,
    @SerializedName("Cadena")
    var cadena: String,
    @SerializedName("DeterminanteGSP")
    var determinanteGSP: Int,
    @SerializedName("Latitud")
    var latitud: Double,
    @SerializedName("Longitud")
    var longitud: Double,
    @SerializedName("Sucursal")
    var sucursal: String

)