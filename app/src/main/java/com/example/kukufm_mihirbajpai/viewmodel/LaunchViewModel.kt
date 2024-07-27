package com.example.kukufm_mihirbajpai.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kukufm_mihirbajpai.model.Launch
import com.example.kukufm_mihirbajpai.model.LocalLaunch
import com.example.kukufm_mihirbajpai.repository.LaunchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LaunchViewModel @Inject constructor(private val repository: LaunchRepository) : ViewModel() {

    private val _launches = MutableLiveData<List<Launch>>()
    val launches: LiveData<List<Launch>> get() = _launches

    private val _favorites = MutableLiveData<List<Int>>()
    val favorites: LiveData<List<Int>> get() = _favorites

    val isLoading = MutableLiveData(true)

    init {
        loadData()
        getFavorites()
    }

    fun loadData(){
        isLoading.value = true
        viewModelScope.launch {
            try {
                _launches.value = repository.getLaunches()
                Log.d("LaunchViewModel", "$launches")
            } catch (e: Exception) {
                Log.d("LaunchViewModel", "$e")
            }
            isLoading.value = false
        }
    }

    fun getFavorites(){
        viewModelScope.launch {
            _favorites.value = repository.getFavorites().map { it.flightNumber }
        }
    }
    fun addFavorite(flightNumber: Int) {
        viewModelScope.launch {
            repository.addFavorite(flightNumber)
            Log.d("LaunchViewModel", "${favorites.value}")
        }
    }

    fun removeFavorite(flightNumber: Int) {
        viewModelScope.launch {
            repository.removeFavorite(flightNumber)
        }
    }

    fun isFavoriteLiveData(flightNumber: Int): LiveData<Boolean> {
        return repository.isFavorite(flightNumber)
    }

    fun getAllLocalData(onSuccess: (List<LocalLaunch>)->Unit) {
        isLoading.value = true
        viewModelScope.launch {
            val localData = repository.getAllLocalData()
            onSuccess(localData)
            isLoading.value = false
        }
    }

    fun insertLocalData(data: LocalLaunch){
        viewModelScope.launch {
            repository.insertData(data)
        }
    }
}
