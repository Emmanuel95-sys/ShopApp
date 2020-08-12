package mx.arturo.triple.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import mx.arturo.triple.R
import mx.arturo.triple.model.localdb.ActiveRoom

class ActiveAdapter() : RecyclerView.Adapter<ActiveAdapter.ViewHolder>(), Filterable {

    var activesList = mutableListOf<ActiveRoom>()
    var activeListAll = listOf<ActiveRoom>()

        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.active_item,
        parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return activesList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentActive = activesList[position]
        //avoiding duplicated items
        holder.setIsRecyclable(false)

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
    override fun getFilter(): Filter {
        return object : Filter(){
            //run on background thread
            override fun performFiltering(charSequence: CharSequence?): FilterResults {
                activesList.addAll(activeListAll)
                var filteredList = mutableListOf<ActiveRoom>()

                if(charSequence.toString().isEmpty()){
                    filteredList.addAll(activeListAll)
                }else{
                    for(active in activeListAll){
                        if(active.cadena.toLowerCase().contains(charSequence.toString().toLowerCase())
                            || active.determinanteGSP.toString().contains(charSequence.toString())
                            ||active.sucursal.toLowerCase().contains(charSequence.toString().toLowerCase())){
                            filteredList.add(active)
                        }
                    }
                }
                val filterResults = FilterResults()
                filterResults.values = filteredList

                return filterResults
            }
            //run on Ui thread
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                activesList.clear()
                activesList.addAll(results?.values as Collection<ActiveRoom>)
                notifyDataSetChanged()
            }
        }
    }

}