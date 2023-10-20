package mx.psl.prototipo.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import mx.psl.prototipo.R
import mx.psl.prototipo.databinding.MenuAdministradorBinding
import mx.psl.prototipo.viewmodel.SharedViewModel

/**
 * @author Pablo Spínola López
 *
 * Pantalla de administrador donde tiene acceso a cada funcionalidad de la aplicación desde
 * registrar asistencias, lotes, nuevos comensales y miembros del comedor.
 *
 */

class MenuAdmin: Fragment() {

    private lateinit var binding : MenuAdministradorBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //return super.onCreateView(inflater, container, savedInstanceState)
        binding = MenuAdministradorBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registrarEventos()
        registrarObservadores()
    }


    private fun registrarObservadores() {
    }


    // Falta para el boton de voluntario
    private fun registrarEventos() {
        binding.btnAsistencia.setOnClickListener {
            findNavController().navigate(R.id.action_menuAdmin_to_asistencia)
        }
        binding.btnComensal.setOnClickListener {
            findNavController().navigate(R.id.action_menuAdmin_to_fechaComensal2)
        }
        binding.btnLote.setOnClickListener {
            findNavController().navigate(R.id.action_menuAdmin_to_fechaCaducidad)
        }
        binding.btnInventario.setOnClickListener {
            findNavController().navigate(R.id.action_menuAdmin_to_inventario)
        }
        binding.btnRegistrarMiembro.setOnClickListener {
            findNavController().navigate(R.id.action_menuAdmin_to_miembroNuevo)
        }
    }
}