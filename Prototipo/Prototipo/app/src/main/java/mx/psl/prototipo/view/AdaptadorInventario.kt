package mx.psl.prototipo.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import mx.psl.prototipo.R
import mx.psl.prototipo.databinding.RenglonProductoBinding
import mx.psl.prototipo.model.ParseInventario

/**
 * @author Pablo Spínola López
 *
 * Obtiene los productos de la base de datos con algunos datos importantes, y mediante un adaptador rellena un
 * RecyclerView que presenta una tabla con la información de inventario
 *
 */

class AdaptadorInventario(private val contexto: Context, var arrInventario: Array<ParseInventario>)
    : RecyclerView.Adapter<AdaptadorInventario.RenglonProducto> ()
{

    var listener: ListenerRecycler? = null

    override fun getItemCount(): Int {
        return arrInventario.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RenglonProducto {
        val binding = RenglonProductoBinding.inflate(LayoutInflater.from(contexto))
        return RenglonProducto(binding.root)
    }

    override fun onBindViewHolder(holder: AdaptadorInventario.RenglonProducto, position: Int) {
        val producto = arrInventario[position]
        holder.set(producto, position + 1)
        holder.vistaInventario.findViewById<Button>(R.id.btnProducto).setOnClickListener {
            listener?.itemClicked(position)
        }
    }


    class RenglonProducto(var vistaInventario: View) : RecyclerView.ViewHolder(vistaInventario) {

        fun set(producto: ParseInventario, position: Int) {
            val cantidad = "${producto.cantidadI - producto.cantidadR} ${producto.unidad}"
            var productoNom = producto.nombreProduto
            if (productoNom.length > 10) {
                productoNom = productoNom.slice(0..10)
            }
            vistaInventario.findViewById<TextView>(R.id.tvNoRenglon).text = position.toString()
            vistaInventario.findViewById<TextView>(R.id.btnProducto).text = productoNom
            vistaInventario.findViewById<TextView>(R.id.tvCategoriaRenglon).text = producto.categoria
            vistaInventario.findViewById<TextView>(R.id.tvCantidadRenglon).text = cantidad
            vistaInventario.findViewById<TextView>(R.id.tvCaducidadRenglon).text = producto.fechac

        }
    }
}