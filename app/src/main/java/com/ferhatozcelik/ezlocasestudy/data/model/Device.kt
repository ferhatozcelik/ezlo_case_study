package com.ferhatozcelik.ezlocasestudy.data.model

import com.google.gson.annotations.SerializedName

data class Device(
    @SerializedName("PK_Device")
    var pkDevice: Int? = null,
    @SerializedName("MacAddress")
    var macAddress: String? = null,
    @SerializedName("PK_DeviceType")
    var pkDeviceType: Int? = null,
    @SerializedName("PK_DeviceSubType")
    var pkDeviceSubType: Int? = null,
    @SerializedName("Server_Device")
    var serverDevice: String? = null,
    @SerializedName("Server_Event")
    var serverEvent: String? = null,
    @SerializedName("PK_Account")
    var pkAccount: Int? = null,
    @SerializedName("Firmware")
    var firmware: String? = null,
    @SerializedName("Server_Account")
    var serverAccount: String? = null,
    @SerializedName("InternalIP")
    var internalIP: String? = null,
    @SerializedName("LastAliveReported")
    var lastAliveReported: String? = null,
    @SerializedName("Platform")
    var platform: String? = null

)