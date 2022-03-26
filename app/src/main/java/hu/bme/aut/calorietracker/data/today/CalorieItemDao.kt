package hu.bme.aut.calorietracker.data.today

import androidx.room.*
import hu.bme.aut.calorietracker.data.day.Day
import hu.bme.aut.calorietracker.data.today.CalorieItem

@Dao
interface CalorieItemDao {
    @Query("SELECT * FROM calorieitem")
    fun getAll(): List<CalorieItem>

    @Insert
    fun insert(shoppingItems: CalorieItem): Long

    @Update
    fun update(shoppingItem: CalorieItem)

    @Delete
    fun deleteItem(shoppingItem: CalorieItem)

    @Query("DELETE FROM calorieitem")
    fun deleteAll()

    @Query("SELECT * FROM calorieitem WHERE date == :searchedDate ")
    fun getByDate(searchedDate: String): List<CalorieItem>
}