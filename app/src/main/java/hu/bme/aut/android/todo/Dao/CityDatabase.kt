package hu.bme.aut.android.todo.Dao

import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.content.Context
import hu.bme.aut.android.todo.model.City


@Database(entities = arrayOf(City::class), version = 1)
abstract class CityDatabase : RoomDatabase() {
    abstract fun CityDao(): CityDao

    companion object {
        private var INSTANCE: CityDatabase? = null

        fun getAppDataBase(context: Context): CityDatabase? {

            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.applicationContext,
                        CityDatabase::class.java, "city.db")
                        .build()
            }
            return INSTANCE!!
        }
        fun destroyDataBase(){
            INSTANCE = null
        }
    }



}

