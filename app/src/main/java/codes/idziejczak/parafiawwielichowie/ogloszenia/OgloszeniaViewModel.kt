package codes.idziejczak.parafiawwielichowie.ogloszenia

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import codes.idziejczak.parafiawwielichowie.network.ParafiaApi
import kotlinx.coroutines.launch

class OgloszeniaViewModel : ViewModel() {
    private val TAG = "loggo-OgloszeniaViewModel"
    val _ogloszeniaList = MutableLiveData<List<String>>()
    val ogloszeniaList: LiveData<List<String>>
        get() = _ogloszeniaList

    init {
        getOgloszenieList()
    }

    fun getOgloszenieList() {
        viewModelScope.launch {
            try {
                val x = mutableListOf<String>()
                for (i in (942..953)) {
                    val stringResult = ParafiaApi.retrofitService.getOgloszenie(i.toString())
                    val stringSpanned =
                        stringResult.select("[style=\"text-align:justify; padding-right:25px\"]")
                            .toString()
                    x.add(stringSpanned)
                }
                x.reverse()
                _ogloszeniaList.value = x
            } catch (e: Exception) {
                Log.i(TAG, "getOgloszenieList: $e")
                _ogloszeniaList.value = listOf("nie", "udało się: ", e.toString())
            }
        }
    }

}