package codes.idziejczak.parafiawwielichowie.ogloszenia

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import codes.idziejczak.parafiawwielichowie.database.AppDatabase
import codes.idziejczak.parafiawwielichowie.repository.OgloszeniaRepository
import kotlinx.coroutines.launch

class OgloszeniaViewModel(application: Application) :
    AndroidViewModel(application) {
    private val TAG = "loggo-OgloszeniaViewModel"

    private val ogloszeniaRepository = OgloszeniaRepository(AppDatabase.getInstance(application))

    private val _eventNetworkError = MutableLiveData(false)
    val eventNetworkError: LiveData<Boolean?>
        get() = _eventNetworkError

    private val _isErrorNetworkShown = MutableLiveData(false)
    val isErrorNetworkShown: LiveData<Boolean>
        get() = _isErrorNetworkShown

    val listAllOgloszenia = ogloszeniaRepository.ogloszeniaList

    init {
        refreshOgloszenia()
    }

    private fun refreshOgloszenia() {
        viewModelScope.launch {
            try {
                ogloszeniaRepository.refreshOglosznenia()
                _eventNetworkError.value = false
                _isErrorNetworkShown.value = false
            } catch (e: Exception) {
                Log.i(TAG, "getOgloszenieList: $e")
                if (listAllOgloszenia.value.isNullOrEmpty()) {
                    _eventNetworkError.value = true
                }
            }
        }
    }

    fun onNetworkErrorShown() {
        _isErrorNetworkShown.value = true
    }

    class Factory(
        private val application: Application
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            if (modelClass.isAssignableFrom(OgloszeniaViewModel::class.java))
                return OgloszeniaViewModel(application) as T
            throw IllegalArgumentException("Unable to construct viewmodel")
        }

    }

}