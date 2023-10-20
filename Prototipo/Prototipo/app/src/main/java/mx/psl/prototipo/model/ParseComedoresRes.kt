package mx.psl.prototipo.model

import com.google.gson.annotations.SerializedName

data class ParseComedoresRes(
    @SerializedName("_id_comedor")
    var idComedor: String,
    @SerializedName("nombre")
    var comedores: String
)
/*
{
    override fun toString(): String {
        return "$idComedor $comedores"
    }
}

 */