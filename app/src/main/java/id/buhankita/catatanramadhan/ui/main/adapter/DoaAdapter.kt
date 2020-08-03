package id.buhankita.catatanramadhan.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.buhankita.catatanramadhan.data.model.doa.Doa
import id.buhankita.catatanramadhan.databinding.ItemListDoaBinding
import kotlinx.android.synthetic.main.item_list_doa.view.*

class DoaAdapter : RecyclerView.Adapter<DoaAdapter.ViewHolder>() {

    var dataList = emptyList<Doa>()

    fun setData(doa: List<Doa>) {
        this.dataList = doa
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ItemListDoaBinding) :
        RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemListDoaBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

        fun bind(doa: Doa) {
            binding.doa = doa
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoaAdapter.ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: DoaAdapter.ViewHolder, position: Int) {
        val doa = dataList[position]
        holder.bind(doa)
        holder.itemView.tvNumber.text = (position+1).toString()
    }
}