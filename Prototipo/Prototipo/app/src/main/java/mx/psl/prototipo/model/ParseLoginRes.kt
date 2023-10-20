package mx.psl.prototipo.model

import com.google.gson.annotations.SerializedName

data class ParseLoginRes(
    @SerializedName("message")
    val message: String,
    @SerializedName("idMiembro")
    val idMiembro: String,
    @SerializedName("idJornada")
    val idJornada: String,
    @SerializedName("cargo")
    val cargo: Int
)
