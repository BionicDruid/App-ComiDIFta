package mx.psl.prototipo.model

import com.google.gson.annotations.SerializedName

data class ParseRegistroComensalRes(
    @SerializedName("id")
    val idNComensal: String,
    @SerializedName("nombre")
    val nombreNComensal: String
)
