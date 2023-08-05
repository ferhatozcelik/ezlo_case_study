package com.ferhatozcelik.ezlocasestudy.data.model

import com.google.gson.annotations.SerializedName

data class DeviceResult(

    @SerializedName("Devices")
    var devices: List<Device> = arrayListOf()

)