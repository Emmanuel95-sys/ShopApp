package mx.arturo.triple.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import mx.arturo.triple.R
import mx.arturo.triple.model.localdb.ActiveRoom

class ActiveAdapter : RecyclerView.Adapter<ActiveAdapter.ViewHolder>() {
    var activesData = listOf<ActiveRoom>()

    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActiveAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.active_item,
        parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return activesData.size
    }

    override fun onBindViewHolder(holder: ActiveAdapter.ViewHolder, position: Int) {
        val currentActive = activesData[position]
        holder.ItemActive.text = currentActive.activo.toString()
        holder.Itemcadena.text = currentActive.cadena
        holder.ItemDeterminante.text = currentActive.determinanteGSP.toString()

        holder.Itemlatitud.text = currentActive.latitud.toString()
        holder.ItemLongitud.text = currentActive.longitud.toString()
        holder.ItemASucursal.text = currentActive.sucursal
    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var ItemActive = itemView.findViewById<TextView>(R.id.tvItemActive)
        var Itemcadena = itemView.findViewById<TextView>(R.id.tvItemCadena)
        var ItemDeterminante = itemView.findViewById<TextView>(R.id.tvItemDeterminante)

        var Itemlatitud = itemView.findViewById<TextView>(R.id.tvItemLatitud)
        var ItemLongitud = itemView.findViewById<TextView>(R.id.tvItemLongitud)
        var ItemASucursal = itemView.findViewById<TextView>(R.id.tvItemSurcursal)
    }
}