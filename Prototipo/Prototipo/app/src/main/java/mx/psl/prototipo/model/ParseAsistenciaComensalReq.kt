package mx.psl.prototipo.model

import com.google.gson.annotations.SerializedName


//TODO
data class ParseAsistenciaComensalReq(
    @SerializedName("idJornada")
    var idJornada: String,
    @SerializedName("llaveUnica")
    var llaveUnica: String,
    @SerializedName("idEncargado")
    var idEncargado: String,
    @SerializedName("aportacion")
    var aportacion: Int
)
