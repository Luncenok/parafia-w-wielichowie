package codes.idziejczak.parafiawwielichowie.ogloszenia

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.text.HtmlCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import codes.idziejczak.parafiawwielichowie.R
import codes.idziejczak.parafiawwielichowie.database.Ogloszenie
import codes.idziejczak.parafiawwielichowie.databinding.ListItemOgloszenieBinding

class OgloszeniaAdapter :
    ListAdapter<Ogloszenie, OgloszeniaAdapter.ViewHolder>(OgloszeniaDiffCallback()) {

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
            it.test.text =
                HtmlCompat.fromHtml(getItem(position).body, HtmlCompat.FROM_HTML_MODE_COMPACT)
        }
    }
}

class OgloszeniaDiffCallback : DiffUtil.ItemCallback<Ogloszenie>() {
    override fun areItemsTheSame(oldItem: Ogloszenie, newItem: Ogloszenie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Ogloszenie, newItem: Ogloszenie): Boolean {
        return oldItem == newItem
    }

}