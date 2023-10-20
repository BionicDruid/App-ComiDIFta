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
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import mx.psl.prototipo.databinding.RegistroLoteBinding
import mx.psl.prototipo.model.ManejarFechas
import mx.psl.prototipo.model.ParseLoteInsercionReq
import mx.psl.prototipo.viewmodel.LlamadaAPIs
import mx.psl.prototipo.viewmodel.SharedViewModel
import java.util.Calendar

/**
 * @author Pablo Spínola López
 *
 * Esta clase se encarga de obtener los datos que subes a través de la vista, estos datos
 * los usa para crear un POST dentro de la base de datos y permitia que puedan hacer un nuevo lote,
 * este lote es posible que lo visualicen en la sección de ver inventario dentro de la aplicación
 * al igual que puedes verlo en la aplicación web
 *
 */

class FechaCaducidad : Fragment()  {


    private lateinit var binding: RegistroLoteBinding
    private lateinit var datePickerDialog : DatePickerDialog
    private var manejarFechas = ManejarFechas()
    private var fechaUsable: String = manejarFechas.getTodaysDate(false)
    private val nuevoLote: LlamadaAPIs by viewModels()

    fun checkTextViews(vararg editTexts: EditText, btnLI: Button) {

        for (editText in editTexts) {
            editText.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    btnLI.isEnabled = editTexts.all { it.text.isNotEmpty() }
                }
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            })
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RegistroLoteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnFechaCaducidad.text = manejarFechas.getTodaysDate(true)

        val nom = binding.etProductoN
        val cant = binding.etCantidad
        val esp = binding.etEspecificaciones
        val btnLt = binding.btnRegistrarLote

        checkTextViews(nom, cant, esp, btnLI = btnLt)
        initDatePicker()
        registrarEventos()
    }

    private fun registrarEventos() {
        binding.btnCancel.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.btnFechaCaducidad.setOnClickListener {
            datePickerDialog.show()
        }
        binding.btnRegistrarLote.setOnClickListener {

            val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
            val idComedor = sharedPref.getString("idComedor","")
            val idMiembro = sharedPref.getString("idMiembro","")

            val infoNuevoLote = ParseLoteInsercionReq(
                nomProducto = binding.etProductoN.text.toString(),
                categoria = binding.spCategorA.selectedItem.toString(),
                especificaciones = binding.etEspecificaciones.text.toString(),
                fechaCaduca = fechaUsable,
                cantidadI = binding.etCantidad.text.toString().toInt(),
                unidad = binding.spUnidad.selectedItem.toString(),
                idEncargado = idMiembro!!
            )

            nuevoLote.insertarLote(idComedor!!, infoNuevoLote)


            findNavController().navigateUp()
        }
    }

    private fun initDatePicker() {
        val dateSetListener = DatePickerDialog.OnDateSetListener { datePicker: DatePicker, anio: Int, mes: Int, dia: Int ->
            val date: CharSequence? = manejarFechas.armaFecha(dia, mes, anio)
            fechaUsable = manejarFechas.armaFechaSQL(dia, mes, anio)
            binding.btnFechaCaducidad.text = date.toString()
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