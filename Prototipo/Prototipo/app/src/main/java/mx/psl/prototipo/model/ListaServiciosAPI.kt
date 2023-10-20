package mx.psl.prototipo.model

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * @author Pablo Spínola López
 *
 * Aquí es donde se define la estructura de cada API a llamar, especificando cuerpo y path
 *
 */

interface ListaServiciosAPI {

    // Gets
    @GET("comedores")
    fun descargarListaComedores():
            Call<List<ParseComedoresRes>>

    @GET("inventario/app/{idComedor}")
    fun descargarInventario(
        @Path("idComedor") param: String):
            Call<List<ParseInventario>>

    // Posts

    @POST("usuario/login")
    fun loginUsuario(
        @Body usuario: ParseLoginReq):
            Call<ParseLoginRes>

    @POST("usuario")
    fun registrarUsuario(
        @Body usuario: ParseNuevoMiembroReq):
            Call<ParseResExito>

    @POST("comensales")
    fun registrarComensal(
        @Body comensal: ParseRegistroComensalReq):
            Call<ParseRegistroComensalRes>

    @POST("asistencia")
    fun registrarAsistencia(
        @Body comensal: ParseAsistenciaComensalReq):
            Call<ParseResExito>

    @POST("codigoQR")
    fun registrarUrl(
        @Body url: ParseQR):
            Call<ParseResExito>

    @POST("lote/agregar/{idComedor}")
    fun insertarLote(
        @Path("idComedor") param: String,
        @Body lote: ParseLoteInsercionReq):
            Call<ParseResExito>

    @GET("lote/consumir/{idLote}")
    fun consumirLote(
        @Path("idLote") param: String,
        @Body lote: ParseLoteEliminar):
            Call<ParseResExito>
}