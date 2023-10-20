package mx.psl.prototipo.model

import com.google.gson.annotations.SerializedName

data class ParseInventario(
    @SerializedName("_id_lote")
    var idLote: String,
    @SerializedName("producto")
    var nombreProduto: String,
    @SerializedName("categoria")
    var categoria: String,
    @SerializedName("cantidad_inicial")
    var cantidadI: Int,
    @SerializedName("cantidad_restada")
    var cantidadR: Int,
    @SerializedName("unidad")
    var unidad: String,
    @SerializedName("fecha_registro")
    var fechar: String,
    @SerializedName("fecha_caducidad")
    var fechac: String,
    @SerializedName("especificaciones")
    var especif: String,
    @SerializedName("producto_excedente")
    var excedente: Int
)
