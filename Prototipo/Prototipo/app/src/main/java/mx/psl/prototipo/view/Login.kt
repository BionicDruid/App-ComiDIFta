package mx.psl.prototipo.view

import android.content.Context
import android.os.Bundle
import android.preference.PreferenceManager
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import mx.psl.prototipo.R
import mx.psl.prototipo.databinding.LoginBinding
import mx.psl.prototipo.model.ParseLoginReq
import mx.psl.prototipo.viewmodel.LlamadaAPIs
import mx.psl.prototipo.viewmodel.SharedViewModel
import java.security.MessageDigest

/**
 * @author Pablo Spínola López
 *
 * Pantalla inicial para acceder a la aplicación, donde el usuario ingresa con su nombre de usuario,
 * contraseña y accede al comedor donde se iniciará una jornada.
 *
 */

class Login : Fragment(){

    private lateinit var binding : LoginBinding
    private val descargarComedores : LlamadaAPIs by viewModels()
    private val logInUsuario : LlamadaAPIs by viewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private var cargo = false

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
        binding = LoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnLogin.isEnabled = false

        val etU = binding.etNombreU
        val passU = binding.etPassword
        val btnLI = binding.btnLogin

        checkTextViews(etU, passU, btnLI = btnLI)
        registrarObservadores()
        registrarEventos()
    }

    override fun onStart() {
        super.onStart()
        descargarComedores.descargarListaComedores()
    }

    private fun registrarObservadores() {
        descargarComedores.listaComedores.observe(viewLifecycleOwner) { lista ->
            descargarComedores.filler()
            val arrComedores = descargarComedores.list.toTypedArray()
            binding.spComedor.adapter = ArrayAdapter(requireContext(),
                android.R.layout.simple_spinner_item, arrComedores)
        }
        logInUsuario.loginExito.observe(viewLifecycleOwner) { responseBody ->
            val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
            val editor = sharedPref.edit()
            editor.apply {
                putString("idMiembro", responseBody.idMiembro)
                putString("idJornada", responseBody.idJornada)
            }.apply()
            //updateData("idMiembro", responseBody.idMiembro)
            //updateData("idJornada", responseBody.idJornada)
            cargo = responseBody.cargo == 1

            /*
            sharedViewModel.data.observe(viewLifecycleOwner) { info ->
                println(info["idMiembro"])
                println(info.values)
            }
            */

            if (cargo) {
                findNavController().navigate(R.id.action_login2_to_menuAdmin)
            } else {
                findNavController().navigate(R.id.action_login2_to_menuVolun)
            }


        }
    }

    fun hashear(pass:String):String{
        val bytes = pass.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        return digest.joinToString("") { "%02x".format(it) }
    }

    // Falta para el boton de voluntario
    private fun registrarEventos() {
        binding.btnLogin.setOnClickListener {
            val idComedor = descargarComedores.map[binding.spComedor.selectedItem]
            //updateData("idComedor", idComedor)
            val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
            val editor = sharedPref.edit()
            editor?.putString("idComedor", idComedor)
            editor?.apply()
            println(idComedor.toString())
            val usuario = ParseLoginReq(
                nombreMiembro = binding.etNombreU.text.toString(),
                password = hashear(binding.etPassword.text.toString()),
                idComedor = idComedor.toString()
            )
            logInUsuario.loginUsuario(usuario)
        }
    }


    fun updateData(key: String, value: String?) {
        sharedViewModel.addValue(key, value)
    }

}