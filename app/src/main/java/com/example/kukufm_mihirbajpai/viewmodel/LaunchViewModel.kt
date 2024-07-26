package com.example.kukufm_mihirbajpai.viewmodel

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

    init {
        viewModelScope.launch {
            try {
                _launches.value = repository.getLaunches()
            } catch (e: Exception) {
                // Handle the error appropriately
            }
        }
    }
}
