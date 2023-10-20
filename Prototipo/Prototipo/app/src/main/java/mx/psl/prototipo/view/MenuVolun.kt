package mx.psl.prototipo.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import mx.psl.prototipo.R
import mx.psl.prototipo.databinding.MenuVoluntarioBinding

/**
 * @author Pablo Spínola López
 *
 * Pantalla de voluntario donde tiene acceso a algunas funcionalidades de la aplicación
 * registrar asistencias, lotes y nuevos comensales.
 *
 */

class MenuVolun : Fragment() {

    private lateinit var binding : MenuVoluntarioBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MenuVoluntarioBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registrarEventos()
    }

    private fun registrarEventos() {
        binding.btnAsistencia.setOnClickListener {
            findNavController().navigate(R.id.action_menuVolun_to_asistencia)
        }
        binding.btnComensal.setOnClickListener {
            findNavController().navigate(R.id.action_menuVolun_to_fechaComensal2)
        }
        binding.btnLote.setOnClickListener {
            findNavController().navigate(R.id.action_menuVolun_to_fechaCaducidad)
        }
        binding.btnInventario.setOnClickListener {
            findNavController().navigate(R.id.action_menuVolun_to_inventario)
        }
    }

}