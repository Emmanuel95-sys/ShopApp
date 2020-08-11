package mx.arturo.triple.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import mx.arturo.triple.R
import mx.arturo.triple.model.localdb.ActiveRoom

class ActiveAdapter : RecyclerView.Adapter<ActiveAdapter.ViewHolder>(){//, Filterable {
    //this list is set by the view model
    var activesData = listOf<ActiveRoom>()
    //filtered list
    //var activesFilterList = mutableListOf<ActiveRoom>()

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

    //implementing filter
//    override fun getFilter(): Filter {
//        return object : Filter(){
//            //run on background thread
//            override fun performFiltering(constraint: CharSequence?): FilterResults {
//                var filteredList = mutableListOf<ActiveRoom>()
//                if(constraint.toString().isEmpty()){
//                    filteredList.addAll(activesData)
//                }else{
//                    for(active in activesData){
//                        if(active.cadena.toLowerCase().
//                            contains(constraint.toString().toLowerCase()) )
//                            filteredList.add(active)
//                    }
//                }
//                var filterResults = FilterResults()
//                filterResults.values = filteredList
//                return filterResults
//            }
//            //run on Ui thread
//            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
//               activesFilterList.clear()
//               activesFilterList.addAll(results?.values as List<ActiveRoom>)
//               notifyDataSetChanged()
//            }
//        }
//    }

}