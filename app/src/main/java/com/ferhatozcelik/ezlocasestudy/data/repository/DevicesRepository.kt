package com.ferhatozcelik.ezlocasestudy.data.repository

import com.ferhatozcelik.ezlocasestudy.data.dao.DevicesDao
import com.ferhatozcelik.ezlocasestudy.data.entity.DeviceEntity
import com.ferhatozcelik.ezlocasestudy.data.model.DeviceModel
import com.ferhatozcelik.ezlocasestudy.data.model.DeviceResult
import com.ferhatozcelik.ezlocasestudy.data.remote.AppApi
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DevicesRepository @Inject constructor(private val appApi: AppApi, private val devicesDao: DevicesDao) {

    private val devicePrefix = "Home Number"

    suspend fun getLocalDeviceList(): DeviceModel {

        val tempList = mutableListOf<DeviceEntity>()

        val localResult = devicesDao.getDevicesData()

        localResult.forEach {
            tempList.add(
                DeviceEntity(
                    id = it.id,
                    deviceName = it.deviceName,
                    pkDevice = it.pkDevice,
                    macAddress = it.macAddress,
                    pkDeviceType = it.pkDeviceType,
                    pkDeviceSubType = it.pkDeviceSubType,
                    serverDevice = it.serverDevice,
                    serverEvent = it.serverEvent,
                    pkAccount = it.pkAccount,
                    firmware = it.firmware,
                    serverAccount = it.serverAccount,
                    internalIP = it.internalIP,
                    platform = it.platform,
                    lastAliveReported = it.lastAliveReported
                )
            )
        }
        return DeviceModel(tempList.toList())
    }

    suspend fun getApiDevicesList(): Response<DeviceResult> {
        return appApi.getDevicesList()
    }

    suspend fun saveDevicesList(devices: DeviceResult) {
        devicesDao.deleteAll()
        var deviceCount = 1
        devices.devices.forEach {
            devicesDao.insert(
                DeviceEntity(
                    deviceName = "$devicePrefix $deviceCount",
                    pkDevice = it.pkDevice,
                    macAddress = it.macAddress,
                    pkDeviceType = it.pkDeviceType,
                    pkDeviceSubType = it.pkDeviceSubType,
                    serverDevice = it.serverDevice,
                    serverEvent = it.serverEvent,
                    pkAccount = it.pkAccount,
                    firmware = it.firmware,
                    serverAccount = it.serverAccount,
                    internalIP = it.internalIP,
                    platform = it.platform,
                    lastAliveReported = it.lastAliveReported
                )
            )
            deviceCount++
        }
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