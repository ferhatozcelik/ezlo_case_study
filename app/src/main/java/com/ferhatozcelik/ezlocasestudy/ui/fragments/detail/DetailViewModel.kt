package com.ferhatozcelik.ezlocasestudy.ui.fragments.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ferhatozcelik.ezlocasestudy.data.repository.DevicesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val devicesRepository: DevicesRepository,) : ViewModel() {
    private val TAG = DetailViewModel::class.java.simpleName
    fun updateDevice(name: String, id: Int)  = viewModelScope.launch {
        devicesRepository.updateDevice(name, id)
    }
}