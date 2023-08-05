package com.ferhatozcelik.ezlocasestudy.data.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ferhatozcelik.ezlocasestudy.data.model.Device
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "device_table")
data class DeviceEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var deviceName: String? = null,
    var pkDevice: Int? = null,
    var macAddress: String? = null,
    var pkDeviceType: Int? = null,
    var pkDeviceSubType: Int? = null,
    var serverDevice: String? = null,
    var serverEvent: String? = null,
    var pkAccount: Int? = null,
    var firmware: String? = null,
    var serverAccount: String? = null,
    var internalIP: String? = null,
    var lastAliveReported: String? = null,
    var platform: String? = null,
    var timestamp: String = System.currentTimeMillis().toString()
) : Parcelable


