package mx.psl.prototipo.model

import com.google.gson.annotations.SerializedName

data class ParseQR(
    @SerializedName("url")
    val url: String,
    @SerializedName("idComedor")
    val idComedor: String
)
