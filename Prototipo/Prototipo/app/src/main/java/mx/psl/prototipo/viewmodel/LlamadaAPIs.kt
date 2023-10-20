package mx.psl.prototipo.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mx.psl.prototipo.model.ListaServiciosAPI
import mx.psl.prototipo.model.ParseComedoresRes
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import mx.psl.prototipo.model.ParseAsistenciaComensalReq
import mx.psl.prototipo.model.ParseInventario
import mx.psl.prototipo.model.ParseLoginReq
import mx.psl.prototipo.model.ParseLoginRes
import mx.psl.prototipo.model.ParseLoteEliminar
import mx.psl.prototipo.model.ParseLoteInsercionReq
import mx.psl.prototipo.model.ParseNuevoMiembroReq
import mx.psl.prototipo.model.ParseQR
import mx.psl.prototipo.model.ParseRegistroComensalReq
import mx.psl.prototipo.model.ParseRegistroComensalRes
import mx.psl.prototipo.model.ParseResExito

/**
 * @author Pablo Spínola López
 * Esta clase se encarga de realizar una llamada a las api's del servidor web, cada una
 * esta nombrada diferente con su endpoint respectivo, esta clase es llamada en las demás
 * para poder hacer los posts y gets
 */

class LlamadaAPIs () : ViewModel() {

    // Gets
    val listaComedores = MutableLiveData<List<ParseComedoresRes>>() //
    val listaInventario = MutableLiveData<List<ParseInventario>>()
    // Posts
    val loginExito = MutableLiveData<ParseLoginRes>() //
    val usuarioAceptado = MutableLiveData<ParseResExito>() //
    val comensalAceptado = MutableLiveData<ParseRegistroComensalRes>() //
    val comesalAsistido = MutableLiveData<ParseResExito>() //
    val urlAceptado = MutableLiveData<ParseResExito>() //
    val loteAceptado = MutableLiveData<ParseResExito>() //
    val loteEliminado = MutableLiveData<ParseResExito>() //


    var list = mutableListOf<String>()
    var map = mutableMapOf<String, String>()

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("http://54.146.167.120:5000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val descargarAPI by lazy {
        retrofit.create(ListaServiciosAPI::class.java)
    }


    fun filler() {
        listaComedores.value?.let { comedores ->
            for (comedor in comedores) {
                list.add(comedor.comedores)
                map[comedor.comedores] = comedor.idComedor
            }
        }
    }

    fun descargarListaComedores() {
        val call = descargarAPI.descargarListaComedores()           //
        call.enqueue(object: Callback<List<ParseComedoresRes>> {    //
            override fun onResponse(
                call: Call<List<ParseComedoresRes>>,                //
                response: Response<List<ParseComedoresRes>>         //
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { responseBody ->
                        println("RESPUESTA: ${responseBody}")
                        listaComedores.value = responseBody      //
                    }
                } else {
                    println("FALLA: ${response.errorBody()}")
                }
            }
            override fun onFailure(call: Call<List<ParseComedoresRes>>, t: Throwable) { //
                println("ERROR: ${t.localizedMessage}")
            }
        })
    }

    fun descargarInventario(idComedor: String) {
        val call = descargarAPI.descargarInventario(idComedor)
        call.enqueue(object: Callback<List<ParseInventario>> {
            override fun onResponse(
                call: Call<List<ParseInventario>>,
                response: Response<List<ParseInventario>>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { responseBody ->
                        listaInventario.value = responseBody
                    }
                } else {
                    println("FALLA: ${response.errorBody()}")
                }
            }
            override fun onFailure(call: Call<List<ParseInventario>>, t: Throwable) { //
                println("ERROR: ${t.localizedMessage}")
            }
        })
    }


    fun loginUsuario(miembro: ParseLoginReq) {
        val call = descargarAPI.loginUsuario(miembro)
        call.enqueue(object: Callback<ParseLoginRes> {
            override fun onResponse(
                call: Call<ParseLoginRes>,
                response: Response<ParseLoginRes>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { responseBody ->
                        println("RESPUESTA: ${responseBody}")
                        loginExito.value = responseBody      //
                    }
                } else {
                    println("FALLA: ${response.errorBody()}, ${response.message()}")
                }
            }
            override fun onFailure(call: Call<ParseLoginRes>, t: Throwable) { //
                println("ERROR: ${t.localizedMessage}")
            }
        })
    }

    fun registrarUsuario(miembro: ParseNuevoMiembroReq) {
        val call = descargarAPI.registrarUsuario(miembro)
        call.enqueue(object: Callback<ParseResExito> {
            override fun onResponse(
                call: Call<ParseResExito>,
                response: Response<ParseResExito>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { responseBody ->
                        println("RESPUESTA: ${responseBody}")
                        usuarioAceptado.value = responseBody      //
                    }
                } else {
                    println("FALLA: ${response.errorBody()}")
                }
            }
            override fun onFailure(call: Call<ParseResExito>, t: Throwable) { //
                println("ERROR: ${t.localizedMessage}")
            }
        })
    }

    fun registrarComensal(comensal: ParseRegistroComensalReq) {
        val call = descargarAPI.registrarComensal(comensal)
        call.enqueue(object: Callback<ParseRegistroComensalRes> {
            override fun onResponse(
                call: Call<ParseRegistroComensalRes>,
                response: Response<ParseRegistroComensalRes>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { responseBody ->
                        println("RESPUESTA: ${responseBody}")
                        comensalAceptado.value = responseBody      //
                    }
                } else {
                    println("FALLA: ${response.errorBody()}")
                }
            }
            override fun onFailure(call: Call<ParseRegistroComensalRes>, t: Throwable) { //
                println("ERROR: ${t.localizedMessage}")
            }
        })
    }

    fun registrarAsistencia(comensal: ParseAsistenciaComensalReq) {
        val call = descargarAPI.registrarAsistencia(comensal)
        call.enqueue(object: Callback<ParseResExito> {
            override fun onResponse(
                call: Call<ParseResExito>,
                response: Response<ParseResExito>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { responseBody ->
                        println("RESPUESTA: ${responseBody}")
                        comesalAsistido.value = responseBody      //
                    }
                } else {
                    println("FALLA: ${response.errorBody()}")
                }
            }
            override fun onFailure(call: Call<ParseResExito>, t: Throwable) { //
                println("ERROR: ${t.localizedMessage}")
            }
        })
    }

    fun registrarUrl(comensal: ParseQR) {
        val call = descargarAPI.registrarUrl(comensal)
        call.enqueue(object: Callback<ParseResExito> {
            override fun onResponse(
                call: Call<ParseResExito>,
                response: Response<ParseResExito>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { responseBody ->
                        println("RESPUESTA: ${responseBody}")
                        urlAceptado.value = responseBody      //
                    }
                } else {
                    println("FALLA: ${response.errorBody()}")
                }
            }
            override fun onFailure(call: Call<ParseResExito>, t: Throwable) { //
                println("ERROR: ${t.localizedMessage}")
            }
        })
    }

    fun insertarLote(idComedor: String, lote: ParseLoteInsercionReq) {
        val call = descargarAPI.insertarLote(idComedor, lote)
        call.enqueue(object: Callback<ParseResExito> {
            override fun onResponse(
                call: Call<ParseResExito>,
                response: Response<ParseResExito>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { responseBody ->
                        println("RESPUESTA: ${responseBody}")
                        loteAceptado.value = responseBody      //
                    }
                } else {
                    println("FALLA: ${response.errorBody()}")
                }
            }
            override fun onFailure(call: Call<ParseResExito>, t: Throwable) { //
                println("ERROR: ${t.localizedMessage}")
            }
        })
    }

    fun consumirLote(idLote: String, cantidad: ParseLoteEliminar) {
        val call = descargarAPI.consumirLote(idLote, cantidad)
        call.enqueue(object: Callback<ParseResExito> {
            override fun onResponse(
                call: Call<ParseResExito>,
                response: Response<ParseResExito>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { responseBody ->
                        println("RESPUESTA: ${responseBody}")
                        loteEliminado.value = responseBody      //
                    }
                } else {
                    println("FALLA: ${response.errorBody()}")
                }
            }
            override fun onFailure(call: Call<ParseResExito>, t: Throwable) { //
                println("ERROR: ${t.localizedMessage}")
            }
        })
    }
}