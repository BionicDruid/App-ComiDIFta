package mx.psl.prototipo.model

import com.google.gson.annotations.SerializedName

data class ParseLoteEliminar (
    @SerializedName("cantidadRestada")
    val cantidad: Int
)