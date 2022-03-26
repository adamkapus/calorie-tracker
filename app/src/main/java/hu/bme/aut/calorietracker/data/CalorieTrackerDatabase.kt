package hu.bme.aut.calorietracker.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import hu.bme.aut.calorietracker.data.today.CalorieItem
import hu.bme.aut.calorietracker.data.today.CalorieItemDao

@Database(entities = [CalorieItem::class], version = 1)
@TypeConverters(value = [CalorieItem.CalorieType::class])
abstract class CalorieTrackerDatabase : RoomDatabase() {
    abstract fun calorieItemDao(): CalorieItemDao

    companion object {
        fun getDatabase(applicationContext: Context): CalorieTrackerDatabase {
            return Room.databaseBuilder(
                applicationContext,
                CalorieTrackerDatabase::class.java,
                "calorietracker"
            ).build();
        }
    }
}