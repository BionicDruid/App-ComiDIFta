package mx.psl.prototipo.model

import com.google.gson.annotations.SerializedName

data class ParseLoteInsercionReq (
    @SerializedName("producto")
    val nomProducto: String,
    @SerializedName("categoria")
    val categoria: String,
    @SerializedName("especificaciones")
    val especificaciones: String,
    @SerializedName("fechaCaducidad")
    val fechaCaduca: String,
    @SerializedName("cantidadInicial")
    val cantidadI: Int,
    @SerializedName("unidad")
    val unidad: String,
    @SerializedName("idEncargado")
    val idEncargado: String
)