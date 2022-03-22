package codes.idziejczak.parafiawwielichowie.ui.niezbedniki

import android.app.Application
import androidx.core.text.HtmlCompat
import androidx.lifecycle.*
import codes.idziejczak.parafiawwielichowie.database.AppDatabase
import codes.idziejczak.parafiawwielichowie.repository.NiezbednikiRepository
import kotlinx.coroutines.launch
import timber.log.Timber

class NiezbednikiViewModel(application: Application) : AndroidViewModel(application) {

    val niezbednikiRepository = NiezbednikiRepository(AppDatabase.getInstance(application))

    private val _eventNetworkError = MutableLiveData(false)
    val eventNetworkError: LiveData<Boolean?>
        get() = _eventNetworkError

    private val _isErrorNetworkShown = MutableLiveData(false)
    val isErrorNetworkShown: LiveData<Boolean>
        get() = _isErrorNetworkShown

    val niezbedniki = niezbednikiRepository.niezbedniki
    val narzeczeniIChrzestni = Transformations.map(niezbedniki) {
        if (it != null)
            HtmlCompat.fromHtml(it.narzeczeniIChrzestni, HtmlCompat.FROM_HTML_MODE_COMPACT)
        else null
    }
    val zmarli = Transformations.map(niezbedniki) {
        if (it != null)
            HtmlCompat.fromHtml(it.zmarli, HtmlCompat.FROM_HTML_MODE_COMPACT)
        else null
    }

    init {
        refreshGrupy()
    }

    fun refreshGrupy() {
        viewModelScope.launch {
            try {
                niezbednikiRepository.refreshNiezbedniki()
                _eventNetworkError.value = false
                _isErrorNetworkShown.value = false
            } catch (e: Exception) {
                Timber.tag("loggo").d("$e")
                if (niezbedniki.value == null) {
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
            if (modelClass.isAssignableFrom(NiezbednikiViewModel::class.java))
                return NiezbednikiViewModel(application) as T
            throw IllegalArgumentException("unable to create viewModel")
        }

    }
}