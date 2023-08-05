package com.ferhatozcelik.ezlocasestudy.data.remote

import com.ferhatozcelik.ezlocasestudy.data.model.DeviceResult
import retrofit2.Response
import retrofit2.http.*

interface AppApi {

    @GET("/test_android/drawable-hdpi/items.test")
    suspend fun getDevicesList(): Response<DeviceResult>

}