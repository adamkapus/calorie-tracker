package hu.bme.aut.calorietracker.data.today

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter

@Entity(tableName = "calorieitem")
data class CalorieItem(
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) var id: Long? = null,
    @ColumnInfo(name = "type") var calorieType: CalorieType,
    @ColumnInfo(name = "label") var label: String,
    @ColumnInfo(name = "amount") var amount: Int,
    @ColumnInfo(name = "date") var date: String)

    {
        enum class CalorieType {
            INTAKE, EXERCISE;
            companion object {
                @JvmStatic
                @TypeConverter
                fun getByOrdinal(ordinal: Int): CalorieType? {
                    var ret: CalorieType? = null
                    for (cat in values()) {
                        if (cat.ordinal == ordinal) {
                            ret = cat
                            break
                        }
                    }
                    return ret
                }

                @JvmStatic
                @TypeConverter
                fun toInt(category: CalorieType): Int {
                    return category.ordinal
                }
            }
        }
}