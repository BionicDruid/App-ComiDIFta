package mx.psl.prototipo.model

import com.google.gson.annotations.SerializedName

data class ParseLoginReq(
    @SerializedName("nombre")
    var nombreMiembro: String,
    @SerializedName("password")
    var password: String,
    @SerializedName("comedor")
    var idComedor: String
)