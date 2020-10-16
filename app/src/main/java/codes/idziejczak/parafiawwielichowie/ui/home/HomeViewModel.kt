package codes.idziejczak.parafiawwielichowie.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class HomeViewModel : ViewModel() {

    private val _navigateToOgloszenia = MutableLiveData<Boolean?>()
    val navigateToOgloszenia: LiveData<Boolean?>
        get() = _navigateToOgloszenia

    private val _navigateToLiturgia = MutableLiveData<Boolean?>()
    val navigateToLiturgia: LiveData<Boolean?>
        get() = _navigateToLiturgia

    fun beginNavigateOgloszenia() {
        _navigateToOgloszenia.value = true
    }

    fun beginNavigateLiturgia() {
        _navigateToLiturgia.value = true
    }

    fun doneNavigatingOgloszenia() {
        _navigateToOgloszenia.value = null
    }

    fun doneNavigatingLiturgia() {
        _navigateToLiturgia.value = null
    }

    class Factory : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            if (modelClass.isAssignableFrom(HomeViewModel::class.java))
                return HomeViewModel() as T
            throw IllegalArgumentException("Unable to construct viewmodel")
        }

    }

}