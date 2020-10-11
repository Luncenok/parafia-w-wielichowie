package codes.idziejczak.parafiawwielichowie.ogloszenia

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import codes.idziejczak.parafiawwielichowie.R
import codes.idziejczak.parafiawwielichowie.databinding.ListItemOgloszenieBinding

class OgloszeniaAdapter : RecyclerView.Adapter<OgloszeniaAdapter.ViewHolder>() {

    var ogloszeniaList = emptyList<String>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ViewHolder(val binding: ListItemOgloszenieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.list_item_ogloszenie
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val withDataBinding: ListItemOgloszenieBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            ViewHolder.LAYOUT,
            parent,
            false
        )
        return ViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.also {
            it.test.text = ogloszeniaList[position]
        }
    }

    override fun getItemCount(): Int = ogloszeniaList.size
}