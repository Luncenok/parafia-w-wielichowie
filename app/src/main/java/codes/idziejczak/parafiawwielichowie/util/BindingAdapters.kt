package codes.idziejczak.parafiawwielichowie.util

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import codes.idziejczak.parafiawwielichowie.database.Ogloszenie

@BindingAdapter("isNetworkError", "list")
fun hideIfNetworkError(view: View, isNetWorkError: Boolean, list: LiveData<List<Ogloszenie>>?) {
    view.visibility = if (list != null) View.GONE else View.VISIBLE

    if (isNetWorkError) {
        view.visibility = View.GONE
    }
}