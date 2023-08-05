package com.ferhatozcelik.ezlocasestudy.ui.fragments.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ferhatozcelik.ezlocasestudy.data.model.Resource
import com.ferhatozcelik.ezlocasestudy.data.model.DeviceResult
import com.ferhatozcelik.ezlocasestudy.data.repository.DevicesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val devicesRepository: DevicesRepository) : ViewModel() {
    private val TAG = HomeViewModel::class.java.simpleName

    val deviceList: MutableLiveData<Resource<Any>> = MutableLiveData()

    fun getDeviceList() = viewModelScope.launch {
        Log.d(TAG, "Get API Device List")
        deviceList.postValue(Resource.Loading)
        safeDeviceList()
    }

    fun getLocalList() = viewModelScope.launch {
        Log.d(TAG, "Get Local Device List")
        getLocalDeviceList()
    }

    fun clearList() = viewModelScope.launch {
        Log.d(TAG, "Device List All Clear")
        devicesRepository.clearList()
        getLocalDeviceList()
    }

    fun deleteByDevice(id: Int) = viewModelScope.launch {
        devicesRepository.deleteByDevice(id)
        getLocalDeviceList()
    }

    private suspend fun getLocalDeviceList() {
        try {
            val resultLocal = devicesRepository.getLocalDeviceList()
            deviceList.postValue(Resource.Success(resultLocal))
        } catch (ex: Exception) {
            Log.e(TAG,  ex.message.toString())
            deviceList.postValue(
                Resource.Error(
                    ex.message.toString(),
                )
            )
        }
    }

    private suspend fun safeDeviceList() {
        try {
            handleDeviceListResponse(devicesRepository.getApiDevicesList())
            getLocalDeviceList()
        } catch (ex: Exception) {
            Log.e(TAG,  ex.message.toString())
            deviceList.postValue(
                Resource.Error(
                    ex.message.toString(),
                )
            )
        }
    }

    private suspend fun handleDeviceListResponse(response: Response<DeviceResult>) {
        if (response.isSuccessful) {
            response.body()?.let {
                Log.e(TAG,  "Response Successful: " + response.body())
                val result = response.body()
                if (result != null) {
                    devicesRepository.saveDevicesList(result)
                }
            }
        }
    }

}