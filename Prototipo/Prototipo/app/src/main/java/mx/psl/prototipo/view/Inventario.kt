package mx.psl.prototipo.view

import android.content.Context
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import mx.psl.prototipo.R
import mx.psl.prototipo.databinding.VerInventarioBinding
import mx.psl.prototipo.model.ManejarFechas
import mx.psl.prototipo.model.ParseInventario
import mx.psl.prototipo.viewmodel.LlamadaAPIs
import mx.psl.prototipo.viewmodel.SharedViewModel

/**
 * @author Pablo Spínola López
 *
 * Esta clase permite que puedas ver el inventario, y todos los lotes dentro de un inventario,
 * usa la base de datos dentro de la aplicación web; esta información la extrae en tiempo
 * real por lo que es de suma importancia mantener una conexión estable con internet
 *
 */

class Inventario : Fragment(), ListenerRecycler
{

    private lateinit var binding: VerInventarioBinding
    private var manejarFechas = ManejarFechas()
    private var adapador: AdaptadorInventario? = null
    private val viewModel: LlamadaAPIs by viewModels()
    private lateinit var arrInventario: Array<ParseInventario>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //return super.onCreateView(inflater, container, savedInstanceState)
        binding = VerInventarioBinding.inflate(layoutInflater)
        configureRV()
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
        val idComedor = sharedPref.getString("idComedor","")
        println("---------------")
        println(idComedor)
        if (idComedor != null) {
            println(idComedor)
            viewModel.descargarInventario(idComedor)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registrarEventos()
    }

    private fun registrarEventos() {
        binding.btnCancel.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun configureRV() {
        val layout = GridLayoutManager(requireContext(), 1)
        binding.rvInventario.layoutManager = layout

        viewModel.listaInventario.observe(viewLifecycleOwner) { lista ->
            arrInventario = lista.toTypedArray()
            adapador = AdaptadorInventario(requireContext(), arrInventario) //
            adapador!!.listener = this
            binding.rvInventario.adapter = adapador
        }
    }

    fun assignVals(position:Int) {

        val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)

        if (sharedPref.getString("loteNom", "default value") == "1"){
            val editor = sharedPref.edit()
            editor.apply{
                putString("c", "1")
                putString("loteId", arrInventario[position].idLote)
                putString("loteNom", arrInventario[position].nombreProduto)
                putString("loteCat", arrInventario[position].categoria)
                putString("loteUn", arrInventario[position].unidad)
                putString("loteFer", manejarFechas.fechaSQLtoDisplay(arrInventario[position].fechar))
                putString("loteFec", manejarFechas.fechaSQLtoDisplay(arrInventario[position].fechac))
                putString("loteEsp", arrInventario[position].especif)
                putString("loteEcc", "*Este producto NO es excedente")
            }.apply()
        } else {
            val editor = sharedPref.edit()
            editor.apply{
                putString("c", "1")
                putString("loteId", arrInventario[position].idLote)
                putString("loteNom", arrInventario[position].nombreProduto)
                putString("loteCat", arrInventario[position].categoria)
                putString("loteCant", (arrInventario[position].cantidadI - arrInventario[position].cantidadR).toString())
                putString("loteUn", arrInventario[position].unidad)
                putString("loteFer", manejarFechas.fechaSQLtoDisplay(arrInventario[position].fechar))
                putString("loteFec", manejarFechas.fechaSQLtoDisplay(arrInventario[position].fechac))
                putString("loteEsp", arrInventario[position].especif)
                putString("loteEcc", "*Este producto NO es excedente")
            }.apply()
        }
    }

    override fun itemClicked(position:Int) {
        assignVals(position)
        findNavController().navigate(R.id.action_inventario_to_loteVisualizado2)

    }


}