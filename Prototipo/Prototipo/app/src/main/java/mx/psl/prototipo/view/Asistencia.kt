package mx.psl.prototipo.view

import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import mx.psl.prototipo.databinding.RegistroAsistenciaBinding
import mx.psl.prototipo.model.ComensalQR
import mx.psl.prototipo.model.ParseAsistenciaComensalReq
import mx.psl.prototipo.viewmodel.LlamadaAPIs
import mx.psl.prototipo.viewmodel.SharedViewModel

/**
 * @author Pablo Spínola López
 *
 * Por medio de un qr, se envía la información del comensal que esta siendo registrado (asistencia)
 * y confirma esta en la tabla correpondiente haciendo referencia al comensal pertinente. Esto
 * se hace por medio del endpoint diseñado anteriomente
 *
 */

class Asistencia : Fragment() {

    private lateinit var binding : RegistroAsistenciaBinding
    private val asistencia: LlamadaAPIs by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //val view = inflater.inflate(R.layout.registro_asistencia, container, false)
        //return view
        binding = RegistroAsistenciaBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnRegistrarComensal.isEnabled = false
        registrarEventos()
    }


    private fun registrarEventos() {
        var aportacion: Int = 0
        val gson = Gson()
        binding.btnCancel.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.btn13.setOnClickListener {
            aportacion = 13
            binding.btn13.isEnabled = false
            binding.btnDonacion.isEnabled = false
            binding.btnRegistrarComensal.isEnabled = true
        }
        binding.btnDonacion.setOnClickListener {
            binding.btn13.isEnabled = false
            binding.btnDonacion.isEnabled = false
            binding.btnRegistrarComensal.isEnabled = true
        }
        binding.btnRegistrarComensal.setOnClickListener {
            val scanner = GmsBarcodeScanning.getClient(requireContext())
            scanner.startScan()
                .addOnSuccessListener { barcode ->
                    println(barcode.rawValue.toString())
                    println("E")

                    //val jsonData = Gson().fromJson(barcode.rawValue, ComensalQR::class.java)
                    val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
                    /*
                    println(jsonData)
                    println(jsonData.llave)
                    */


                    val infoAsistencia = ParseAsistenciaComensalReq(
                        idJornada = sharedPref.getString("idJornada", "default value")!!,
                        llaveUnica = "LlaveAsistencia",
                        idEncargado = sharedPref.getString("idMiembro", "default value")!!,
                        aportacion = aportacion
                    )
                    asistencia.registrarAsistencia(infoAsistencia)

                }
                .addOnFailureListener { e ->
                    println("owo")
                }
        }
    }

}