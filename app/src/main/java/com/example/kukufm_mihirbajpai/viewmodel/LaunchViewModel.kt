package com.example.kukufm_mihirbajpai.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kukufm_mihirbajpai.model.data.Launch
import com.example.kukufm_mihirbajpai.model.data.LocalLaunch
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

    // Get data from api
    fun loadData() {
        isLoading.value = true
        viewModelScope.launch {
            try {
                _launches.value = repository.getLaunches()
                Log.d("LaunchViewModel", "Launches = $launches")
            } catch (e: Exception) {
                Log.d("LaunchViewModel", "Error = $e")
            }
            isLoading.value = false
        }
    }

    // Return favorite launch's flight numbers list
    fun getFavorites() {
        viewModelScope.launch {
            _favorites.value = repository.getFavorites().map { it.flightNumber }
            Log.d("LaunchViewModel", "Favorites = ${_favorites.value}")

        }
    }

    fun addFavorite(flightNumber: Int) {
        viewModelScope.launch {
            repository.addFavorite(flightNumber = flightNumber)
            Log.d("LaunchViewModel", "Added $flightNumber")

        }
    }

    fun removeFavorite(flightNumber: Int) {
        viewModelScope.launch {
            repository.removeFavorite(flightNumber = flightNumber)
            Log.d("LaunchViewModel", "Removed $flightNumber")
        }
    }

    // Return that current item is saved in favorite or not
    fun isFavoriteLiveData(flightNumber: Int): LiveData<Boolean> {
        return repository.isFavorite(flightNumber = flightNumber)
    }

    fun getAllLocalData(onSuccess: (List<LocalLaunch>) -> Unit) {
        isLoading.value = true
        viewModelScope.launch {
            val localData = repository.getAllLocalData()
            onSuccess(localData)
            Log.d("LaunchViewModel", "Local Data = $localData")
            isLoading.value = false
        }
    }

    // Insert local data if empty
    fun insertLocalData(data: LocalLaunch) {
        viewModelScope.launch {
            repository.insertData(data = data)
            Log.d("LaunchViewModel", "Data = $data")
        }
    }

    companion object {
        const val KEY_FLIGHT_NUMBER = "flightNumber"
        const val TIME_FORMAT = "dd-MM-yy HH:mm"
        const val TXT_PAYLOAD = "Payload"
    }
}
