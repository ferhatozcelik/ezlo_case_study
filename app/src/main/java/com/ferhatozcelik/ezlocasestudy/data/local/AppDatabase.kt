package com.ferhatozcelik.ezlocasestudy.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ferhatozcelik.ezlocasestudy.data.dao.DevicesDao
import com.ferhatozcelik.ezlocasestudy.data.entity.DeviceEntity
import com.ferhatozcelik.ezlocasestudy.di.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject
import javax.inject.Provider

@Database(entities = [DeviceEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getDevicesDao(): DevicesDao

    class Callback @Inject constructor(private val database: Provider<AppDatabase>,
                                       @ApplicationScope private val applicationScope: CoroutineScope) : RoomDatabase.Callback()
}