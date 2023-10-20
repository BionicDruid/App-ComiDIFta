package mx.psl.prototipo.model

import com.google.gson.annotations.SerializedName

data class ParseRegistroComensalReq(
    @SerializedName("nombre")
    val nombre: String,
    @SerializedName("apellido")
    val apellido: String,
    @SerializedName("fechaNacimiento")
    val fechaNacimiento: String,
    @SerializedName("llaveUnica")
    val llaveU: String
)
