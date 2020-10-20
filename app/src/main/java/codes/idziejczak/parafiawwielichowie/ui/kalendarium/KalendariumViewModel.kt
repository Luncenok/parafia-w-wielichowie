package codes.idziejczak.parafiawwielichowie.ui.kalendarium

import android.app.Application
import androidx.core.text.HtmlCompat
import androidx.lifecycle.*
import codes.idziejczak.parafiawwielichowie.database.AppDatabase
import codes.idziejczak.parafiawwielichowie.repository.KalendariumRepository
import kotlinx.coroutines.launch
import timber.log.Timber

class KalendariumViewModel(application: Application) : AndroidViewModel(application) {

    val kalendariumRepository = KalendariumRepository(AppDatabase.getInstance(application))

    private val _eventNetworkError = MutableLiveData(false)
    val eventNetworkError: LiveData<Boolean?>
        get() = _eventNetworkError

    private val _isErrorNetworkShown = MutableLiveData(false)
    val isErrorNetworkShown: LiveData<Boolean>
        get() = _isErrorNetworkShown

    val kalendarium = kalendariumRepository.kalendarium
    val tekstRoczSmierci = Transformations.map(kalendarium) {
        if (it != null)
            HtmlCompat.fromHtml(it.roczSmierci, HtmlCompat.FROM_HTML_MODE_COMPACT)
        else null
    }
    val tekstRoczUrodzin = Transformations.map(kalendarium) {
        if (it != null)
            HtmlCompat.fromHtml(it.roczUrodzin, HtmlCompat.FROM_HTML_MODE_COMPACT)
        else null
    }

    init {
        refreshGrupy()
    }

    fun refreshGrupy() {
        viewModelScope.launch {
            try {
                kalendariumRepository.refreshKalendarium()
                _eventNetworkError.value = false
                _isErrorNetworkShown.value = false
            } catch (e: Exception) {
                Timber.tag("loggo").d("$e")
                if (kalendarium.value == null) {
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
            if (modelClass.isAssignableFrom(KalendariumViewModel::class.java))
                return KalendariumViewModel(application) as T
            throw IllegalArgumentException("unable to create viewModel")
        }

    }
}