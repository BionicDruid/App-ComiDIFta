package mx.psl.prototipo.model

import java.util.Calendar

class ManejarFechas {

    val meses = listOf(
        "Ene",
        "Feb",
        "Mar",
        "Abr",
        "May",
        "Jun",
        "Jul",
        "Ago",
        "Sept",
        "Oct",
        "Nov",
        "Dic")

    private fun numTo2DString(num: Int): String {
        if (num < 10) {
            return "0%d".format(num)
        } else {
            return num.toString()
        }
    }

    fun armaFecha(dia: Int, mes: Int, anio: Int): String {
        return dia.toString() + " " + meses[mes] + " " + anio.toString()
    }

    fun getTodaysDate(forDisplay: Boolean): String {
        val cal = Calendar.getInstance()
        val anio = cal.get(Calendar.YEAR)
        val mes = cal.get(Calendar.MONTH)
        val dia = cal.get(Calendar.DAY_OF_MONTH)
        if (forDisplay) {
            return armaFecha(dia, mes, anio)
        } else {
            return anio.toString() + "-" + numTo2DString(mes + 1) + "-" + numTo2DString(dia)
        }
    }

    fun armaFechaSQL(dia: Int, mes: Int, anio: Int): String {
        return anio.toString() + "-" + numTo2DString(mes + 1) + "-" + numTo2DString(dia)
    }

    fun fechaSQLtoDisplay(fechaSQL: String): String {
        val anio = fechaSQL.substring(0,3)
        val mes = fechaSQL.substring(5,6)
        val dia = fechaSQL.substring(8,9)

        return "$dia/$mes/$anio"
    }
}