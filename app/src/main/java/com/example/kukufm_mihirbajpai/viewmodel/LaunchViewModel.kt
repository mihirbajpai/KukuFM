package com.example.kukufm_mihirbajpai.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kukufm_mihirbajpai.model.Launch
import com.example.kukufm_mihirbajpai.repository.LaunchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LaunchViewModel @Inject constructor(private val repository: LaunchRepository) : ViewModel() {

    private val _launches = MutableLiveData<List<Launch>>()
    val launches: LiveData<List<Launch>> get() = _launches

    val isLoading = MutableLiveData(true)

    init {
        loadData()
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
}
