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
                val documentResult = ParafiaApi.retrofitService.getIdOgloszenia()
                val idLast =
                    documentResult.select("[style=\"float: left; \"] a:first-of-type").attr("href")
                        .replace(
                            "ogloszenia,parafialne,", ""
                        ).replace(".html", "").toInt()
                val list = mutableListOf<String>()
                for (i in idLast downTo idLast - 10) {
                    val documentResult2 = ParafiaApi.retrofitService.getOgloszenie(i.toString())
                    val stringSpanned =
                        documentResult2.select("[style=\"text-align:justify; padding-right:25px\"]")
                            .toString()
                    list.add(stringSpanned)
                    _ogloszeniaList.value = list
                }
            } catch (e: Exception) {
                Log.i(TAG, "getOgloszenieList: $e")
                _ogloszeniaList.value = listOf("nie udało się: ", e.toString())
            }
        }
    }

}