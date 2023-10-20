package mx.psl.prototipo.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import mx.psl.prototipo.databinding.RegistroMiembroBinding
import mx.psl.prototipo.model.ParseNuevoMiembroReq
import mx.psl.prototipo.viewmodel.LlamadaAPIs
import mx.psl.prototipo.viewmodel.SharedViewModel
import java.security.MessageDigest

/**
 * @author: Pablo Spínola López
 *
 * Aquí es donde se registra un nuevo miembro
 * Se reciben los valores y se llaman los apis para tener un nuevo miembro,
 * voluntario o personal del dif
 */

class MiembroNuevo : Fragment() {

    private lateinit var binding: RegistroMiembroBinding
    private val generarUsuario: LlamadaAPIs by viewModels()

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
        //return super.onCreateView(inflater, container, savedInstanceState)
        binding = RegistroMiembroBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnRegistrarMiembro.isEnabled = false

        val etNom = binding.etNomMiembro
        val etApe = binding.etApMiembro
        val etTel = binding.etTelMiembro
        val etPas = binding.stPwdMiembro
        val btnLI = binding.btnRegistrarMiembro

        checkTextViews(etNom, etApe, etTel, etPas, btnLI = btnLI)
        registrarEventos()
    }

    fun hashear(pass:String):String{
        val bytes = pass.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        return digest.joinToString("") { "%02x".format(it) }
    }

    private fun registrarEventos() {
        binding.btnCancel.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.btnRegistrarMiembro.setOnClickListener {
            // TODO: revisar que esté todo lleno
            val cargoStr = binding.spnCargo.selectedItem.toString()
            var cargo = false
            if (cargoStr == "Administrador")
            {
                cargo = true
            }
            val data = ParseNuevoMiembroReq(
                nombre = binding.etNomMiembro.text.toString(),
                apellido = binding.etApMiembro.text.toString(),
                telefono = binding.etTelMiembro.text.toString().toBigInteger(),
                password = hashear(binding.stPwdMiembro.text.toString()),
                admin = cargo
            )

            generarUsuario.registrarUsuario(data)
            findNavController().navigateUp()
        }

    }

}