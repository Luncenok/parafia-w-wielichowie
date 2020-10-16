package codes.idziejczak.parafiawwielichowie.util

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("isNetworkError", "list")
fun hideIfNetworkError(view: View, isNetWorkError: Boolean, list: Any?) {
    view.visibility = if (list != null) View.GONE else View.VISIBLE

    if (isNetWorkError) {
        view.visibility = View.GONE
    }
}