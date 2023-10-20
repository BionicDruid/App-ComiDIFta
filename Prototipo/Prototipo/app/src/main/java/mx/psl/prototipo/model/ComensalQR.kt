package mx.psl.prototipo.model

import com.google.gson.annotations.SerializedName

data class ComensalQR(
    @SerializedName("nombre")
    var nombre: String,
    @SerializedName("llave_unica")
    var llave: String
)
