package codes.idziejczak.parafiawwielichowie.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class HomeViewModel : ViewModel() {

    private val _navigateToOgloszenia = MutableLiveData<Boolean?>()
    val navigateToOgloszenia: LiveData<Boolean?>
        get() = _navigateToOgloszenia

    private val _navigateToKalendarium = MutableLiveData<Boolean?>()
    val navigateToKalendarium: LiveData<Boolean?>
        get() = _navigateToKalendarium

    private val _navigateToGrupy = MutableLiveData<Boolean?>()
    val navigateToGrupy: LiveData<Boolean?>
        get() = _navigateToGrupy

    private val _navigateToNiezbedniki = MutableLiveData<Boolean?>()
    val navigateToNiezbedniki: LiveData<Boolean?>
        get() = _navigateToNiezbedniki

    fun beginNavigateOgloszenia() {
        _navigateToOgloszenia.value = true
    }

    fun beginNavigateKalendarium() {
        _navigateToKalendarium.value = true
    }

    fun beginNavigateGrupy() {
        _navigateToGrupy.value = true
    }

    fun beginNavigateNiezbedniki() {
        _navigateToNiezbedniki.value = true
    }

    fun doneNavigatingOgloszenia() {
        _navigateToOgloszenia.value = null
    }

    fun doneNavigatingKalendarium() {
        _navigateToKalendarium.value = null
    }

    fun doneNavigatingGrupy() {
        _navigateToGrupy.value = null
    }

    fun doneNavigatingNiezbedniki() {
        _navigateToNiezbedniki.value = null
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