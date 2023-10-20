package mx.psl.prototipo.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.preference.PreferenceManager
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import mx.psl.prototipo.databinding.VerLoteBinding
import mx.psl.prototipo.model.ManejarFechas
import mx.psl.prototipo.model.ParseLoteEliminar
import mx.psl.prototipo.viewmodel.LlamadaAPIs
import mx.psl.prototipo.viewmodel.SharedViewModel

/**
 * @author Pablo Spínola López
 *
 * Fragmento para la visualización individual de lotes con detalles de su registro, así como la
 * opción de consumir la cantidad existente de dicho lote seleccionado.
 *
 */

class LoteVisualizado: Fragment()  {

    private lateinit var binding : VerLoteBinding
    private val eliminarLote: LlamadaAPIs by viewModels()

    fun checkTextViews(editText: EditText, btnLI: Button) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                btnLI.isEnabled = editText.text.isNotEmpty()}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //return super.onCreateView(inflater, container, savedInstanceState)
        binding = VerLoteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        llenarTextos()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnFinCons.isEnabled = false
        checkTextViews(binding.etConsCant, binding.btnFinCons)
        registrarEventos()
    }


    private fun llenarTextos() {
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)

        binding.textView4.text = sharedPref.getString("loteNom", "default value")
        binding.tvCategoria.text = sharedPref.getString("loteCat", "default value")
        binding.tvCantidad.text = sharedPref.getString("loteCant", "default value")
        binding.tvUnidad.text = sharedPref.getString("loteUn", "default value")
        binding.tvFechaRegistro.text = sharedPref.getString("loteFer", "default value")
        binding.tvFechaCaducidad.text = sharedPref.getString("loteFec", "default value")
        binding.tvEspecificaciones.text = sharedPref.getString("loteEsp", "default value")
        binding.tvExcedente.text = sharedPref.getString("loteEcc", "default value")

    }


    private fun registrarEventos() {
        binding.btnCancel.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnFinCons.setOnClickListener {
            /*
            val cantidadE = binding.etConsCant.text.toString().toInt()
            val loteE = ParseLoteEliminar (
                cantidad = cantidadE
            )
            val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
            eliminarLote.consumirLote(sharedPref.getString("loteId", "default value")!!, loteE)
        }
        */
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = sharedPref.edit()
        editor.apply{
            putString("loteCant", ((sharedPref.getString("loteCant", "20")!!.toInt()-binding.etConsCant.text.toString().toInt())).toString())
        }.apply()
        findNavController().navigateUp()
    }}

}