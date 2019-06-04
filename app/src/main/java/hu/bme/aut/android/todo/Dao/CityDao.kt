package hu.bme.aut.android.todo.Dao

import android.arch.persistence.room.*
import hu.bme.aut.android.todo.model.City


@Dao
interface CityDao {

    @Query("DELETE FROM City")
    fun deleteAll()

    @Query("SELECT * FROM City")
    fun getAll():List<City>

    @Insert
    fun insert(city: City): Long

    @Update
    fun update(city: City)

    @Delete
    fun deleteItem(city: City)
}