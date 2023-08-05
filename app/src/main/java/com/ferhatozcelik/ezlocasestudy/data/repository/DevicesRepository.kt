package com.ferhatozcelik.ezlocasestudy.data.repository

import com.ferhatozcelik.ezlocasestudy.data.dao.DevicesDao
import com.ferhatozcelik.ezlocasestudy.data.entity.DeviceEntity
import com.ferhatozcelik.ezlocasestudy.data.model.DeviceModel
import com.ferhatozcelik.ezlocasestudy.data.model.DeviceResult
import com.ferhatozcelik.ezlocasestudy.data.model.toDeviceEntity
import com.ferhatozcelik.ezlocasestudy.data.remote.AppApi
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DevicesRepository @Inject constructor(private val appApi: AppApi, private val devicesDao: DevicesDao) {

    suspend fun getLocalDeviceList() = devicesDao.getDevicesData()

    suspend fun getApiDevicesList(): Response<DeviceResult> {
        return appApi.getDevicesList()
    }

    suspend fun saveDevicesList(devices: DeviceResult) {
        devicesDao.deleteAll()
        var deviceCount = 1
        val tempList = mutableListOf<DeviceEntity>()

        devices.devices.forEach { device ->
            tempList.add(device.toDeviceEntity(deviceCount))
            deviceCount++
        }
        devicesDao.insertList(tempList)
    }

    suspend fun clearList() {
        devicesDao.deleteAll()
    }

    suspend fun updateDevice(name: String, id: Int) {
        devicesDao.updateDevice(name, id)
    }

    suspend fun deleteByDevice(id: Int) {
        devicesDao.deleteByDevice(id)
    }
}