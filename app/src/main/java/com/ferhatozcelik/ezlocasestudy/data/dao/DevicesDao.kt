package com.ferhatozcelik.ezlocasestudy.data.dao

import androidx.room.*
import com.ferhatozcelik.ezlocasestudy.data.entity.DeviceEntity

@Dao interface DevicesDao {

    @Query("SELECT * FROM device_table")
    suspend fun getDevicesData(): List<DeviceEntity>

    @Query("UPDATE device_table SET deviceName = :name WHERE id = :id")
    suspend fun updateDevice(name: String, id: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(search: DeviceEntity)

    @Update
    suspend fun update(search: DeviceEntity)

    @Delete
    suspend fun delete(search: DeviceEntity)

    @Query("DELETE FROM device_table")
    suspend fun deleteAll()

}