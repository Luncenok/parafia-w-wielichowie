package codes.idziejczak.parafiawwielichowie.ui.informacje

import android.app.Application
import androidx.core.text.HtmlCompat
import androidx.lifecycle.*
import codes.idziejczak.parafiawwielichowie.database.AppDatabase
import codes.idziejczak.parafiawwielichowie.repository.InformacjeRepository
import kotlinx.coroutines.launch
import timber.log.Timber

class InformacjeViewModel(application: Application) : AndroidViewModel(application) {
    val informacjeRepository = InformacjeRepository(AppDatabase.getInstance(application))

    private val _eventNetworkError = MutableLiveData(false)
    val eventNetworkError: LiveData<Boolean?>
        get() = _eventNetworkError

    private val _isErrorNetworkShown = MutableLiveData(false)
    val isErrorNetworkShown: LiveData<Boolean>
        get() = _isErrorNetworkShown

    val informacje = informacjeRepository.informacje

    val tekst = Transformations.map(informacje) {
        if (it != null)
            HtmlCompat.fromHtml(it.body, HtmlCompat.FROM_HTML_MODE_COMPACT)
        else null
    }

    init {
        refreshInformacje()
    }

    fun refreshInformacje() {
        viewModelScope.launch {
            try {
                informacjeRepository.refreshInformacje()
                _eventNetworkError.value = false
                _isErrorNetworkShown.value = false
            } catch (e: Exception) {
                Timber.tag("loggo").d("$e")
                if (informacje.value == null) {
                    _eventNetworkError.value = true
                }
            }
        }
    }


    fun onNetworkErrorShown() {
        _isErrorNetworkShown.value = true
    }

    class Factory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            if (modelClass.isAssignableFrom(InformacjeViewModel::class.java))
                return InformacjeViewModel(application) as T
            throw IllegalArgumentException("unable to create view model")
        }
    }
}