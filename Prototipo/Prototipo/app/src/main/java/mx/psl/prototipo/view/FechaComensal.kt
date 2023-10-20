package mx.psl.prototipo.view

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.preference.PreferenceManager
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import mx.psl.prototipo.databinding.RegistroComensalBinding
import mx.psl.prototipo.model.ComensalQR
import mx.psl.prototipo.model.ManejarFechas
import mx.psl.prototipo.model.ParseAsistenciaComensalReq
import mx.psl.prototipo.model.ParseQR
import mx.psl.prototipo.model.ParseRegistroComensalReq
import mx.psl.prototipo.viewmodel.LlamadaAPIs
import mx.psl.prototipo.viewmodel.SharedViewModel
import java.security.SecureRandom
import java.util.Calendar

/**
 * @author Pablo Spínola López
 *
 * Esta clase obtiene los datos de la información de un nuevo comensal, inventario y el
 * login a través de la base de datos
 * Se manda a llamar a través de endpoints para hacer subidas a la base de datos
 *
 */

class FechaComensal : Fragment()  {

    private lateinit var binding: RegistroComensalBinding
    private lateinit var datePickerDialog : DatePickerDialog
    private var manejarFechas = ManejarFechas()
    private var fechaUsable: String = manejarFechas.getTodaysDate(false)
    private var botonAceptadoForm = false
    private val generarComensal: LlamadaAPIs by viewModels()
    private val asistencia: LlamadaAPIs by viewModels()
    private val generarUrl: LlamadaAPIs by viewModels()

    fun checkTextViews(vararg editTexts: EditText) {

        for (editText in editTexts) {
            editText.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    botonAceptadoForm = editTexts.all { it.text.isNotEmpty() }
                }
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            })
        }
    }

    fun llaveComensal(nombre: String, apellido: String): String{
        val n1 = SecureRandom().nextInt(10001)
        return nombre.substring(0,2) + apellido.takeLast(2) + fechaUsable + n1.toString()
    }

    fun generarQR(nombre: String, llave: String): String{
        val jsonalmacenado = ComensalQR(nombre = "\"$nombre\"", llave = "\"$llave\"")
        val jsonString = jsonalmacenado.toString()
        val qrGenerado = "https://api.qrserver.com/v1/create-qr-code/?data=$jsonString&size=200x200"
        return qrGenerado
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RegistroComensalBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnFechaNComensal.text = manejarFechas.getTodaysDate(true)
        binding.btnRegistrarComensal.isEnabled = false

        val etNom = binding.etNewComensalN
        val etApe = binding.etNewComensalA

        checkTextViews(etNom, etApe)
        initDatePicker()
        registrarEventos()
    }


    private fun registrarEventos() {
        var aportacion: Int = 0
        binding.btnCancel.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.btnFechaNComensal.setOnClickListener {
            datePickerDialog.show()
        }
        binding.btn13.setOnClickListener {
            aportacion = 13
            binding.btn13.isEnabled = false
            binding.btnDonacion.isEnabled = false
            binding.btnRegistrarComensal.isEnabled = botonAceptadoForm and true
        }
        binding.btnDonacion.setOnClickListener {
            binding.btn13.isEnabled = false
            binding.btnDonacion.isEnabled = false
            binding.btnRegistrarComensal.isEnabled = botonAceptadoForm and true
        }
        binding.btnRegistrarComensal.setOnClickListener {
            val nombreComensal = binding.etNewComensalN.text.toString()
            val apellidoComensal = binding.etNewComensalA.text.toString()
            val llaveU = llaveComensal(nombreComensal, apellidoComensal)
            val infoComensal = ParseRegistroComensalReq(
                nombre = nombreComensal,
                apellido = apellidoComensal,
                fechaNacimiento = fechaUsable,
                llaveU = llaveU
            )

            generarComensal.registrarComensal(infoComensal)

            val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
            val idComedor = sharedPref.getString("idComedor","")
            val idJornada = sharedPref.getString("idJornada","")
            val idMiembro = sharedPref.getString("idMiembro","")

            val infoAsistencia = ParseAsistenciaComensalReq(
                idJornada = idJornada!!,
                llaveUnica = llaveU,
                idEncargado = idMiembro!!,
                aportacion = aportacion
            )
            asistencia.registrarAsistencia(infoAsistencia)

            val infoQR = ParseQR(
                url = generarQR(nombreComensal, llaveU),
                idComedor = idComedor!!
            )
            println(infoQR.url)
            generarUrl.registrarUrl(infoQR)
            findNavController().navigateUp()
        }
    }

    private fun initDatePicker() {
        val dateSetListener = DatePickerDialog.OnDateSetListener { datePicker: DatePicker, anio: Int, mes: Int, dia: Int ->
            val date: CharSequence? = manejarFechas.armaFecha(dia, mes, anio)
            fechaUsable = manejarFechas.armaFechaSQL(dia, mes, anio)
            binding.btnFechaNComensal.text = date.toString()
        }

        val calendario = Calendar.getInstance()
        val anio: Int = calendario.get(Calendar.YEAR)
        val mes: Int = calendario.get(Calendar.MONTH)
        val dia: Int = calendario.get(Calendar.DAY_OF_MONTH)

        val estilo = AlertDialog.THEME_HOLO_LIGHT

        datePickerDialog = DatePickerDialog(requireContext(), estilo, dateSetListener, anio, mes, dia)
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis())
    }

}