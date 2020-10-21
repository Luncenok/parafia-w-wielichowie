package codes.idziejczak.parafiawwielichowie.ui.kontakt

import android.app.Application
import androidx.core.text.HtmlCompat
import androidx.lifecycle.*
import codes.idziejczak.parafiawwielichowie.database.AppDatabase
import codes.idziejczak.parafiawwielichowie.repository.KontaktRepository
import kotlinx.coroutines.launch
import timber.log.Timber

class KontaktViewModel(application: Application) : AndroidViewModel(application) {
    val kontaktRepository = KontaktRepository(AppDatabase.getInstance(application))

    private val _eventNetworkError = MutableLiveData(false)
    val eventNetworkError: LiveData<Boolean?>
        get() = _eventNetworkError

    private val _isErrorNetworkShown = MutableLiveData(false)
    val isErrorNetworkShown: LiveData<Boolean>
        get() = _isErrorNetworkShown

    val kontakt = kontaktRepository.kontakt

    val tekst = Transformations.map(kontakt) {
        if (it != null)
            HtmlCompat.fromHtml(it.body, HtmlCompat.FROM_HTML_MODE_COMPACT)
        else null
    }

    init {
        refreshKontakt()
    }

    fun refreshKontakt() {
        viewModelScope.launch {
            try {
                kontaktRepository.refreshKontakt()
                _eventNetworkError.value = false
                _isErrorNetworkShown.value = false
            } catch (e: Exception) {
                Timber.tag("loggo").d("$e")
                if (kontakt.value == null) {
                    _eventNetworkError.value = true
                }
            }
        }
    }


    fun onNetworkErrorShown() {
        _isErrorNetworkShown.value = true
    }

    class Factory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            if (modelClass.isAssignableFrom(KontaktViewModel::class.java))
                return KontaktViewModel(application) as T
            throw IllegalArgumentException("unable to create view model")
        }

    }
}