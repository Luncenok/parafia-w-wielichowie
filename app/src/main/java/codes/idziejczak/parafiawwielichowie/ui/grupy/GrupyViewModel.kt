package codes.idziejczak.parafiawwielichowie.ui.grupy

import android.app.Application
import androidx.core.text.HtmlCompat
import androidx.lifecycle.*
import codes.idziejczak.parafiawwielichowie.database.AppDatabase
import codes.idziejczak.parafiawwielichowie.repository.GrupyRepository
import kotlinx.coroutines.launch
import timber.log.Timber

class GrupyViewModel(application: Application) : AndroidViewModel(application) {

    val grupyRepository = GrupyRepository(AppDatabase.getInstance(application))

    private val _eventNetworkError = MutableLiveData(false)
    val eventNetworkError: LiveData<Boolean?>
        get() = _eventNetworkError

    private val _isErrorNetworkShown = MutableLiveData(false)
    val isErrorNetworkShown: LiveData<Boolean>
        get() = _isErrorNetworkShown

    val grupy = grupyRepository.grupy
    val tekst = Transformations.map(grupy) {
        if (it != null)
            HtmlCompat.fromHtml(it.body, HtmlCompat.FROM_HTML_MODE_COMPACT)
        else null
    }

    init {
        refreshGrupy()
    }

    fun refreshGrupy() {
        viewModelScope.launch {
            try {
                grupyRepository.refreshGrupy()
                _eventNetworkError.value = false
                _isErrorNetworkShown.value = false
            } catch (e: Exception) {
                Timber.tag("loggo").d("$e")
                if (grupy.value == null) {
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
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            if (modelClass.isAssignableFrom(GrupyViewModel::class.java))
                return GrupyViewModel(application) as T
            throw IllegalArgumentException("Unable to construct viewmodel")
        }

    }
}