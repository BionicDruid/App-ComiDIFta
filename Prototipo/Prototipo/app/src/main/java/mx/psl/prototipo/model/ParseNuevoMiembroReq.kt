package mx.psl.prototipo.model

import com.google.gson.annotations.SerializedName
import java.math.BigInteger

data class ParseNuevoMiembroReq(
    @SerializedName("nombre")
    var nombre: String,
    @SerializedName("apellido")
    var apellido: String,
    @SerializedName("telefono")
    var telefono: BigInteger,
    @SerializedName("password")
    var password: String,
    @SerializedName("cargo")
    var admin: Boolean
)
