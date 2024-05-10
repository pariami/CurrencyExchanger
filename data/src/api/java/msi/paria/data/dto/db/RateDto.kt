package msi.paria.data.dto.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import msi.paria.domain.model.Currency

@Entity(tableName = "rate")
@TypeConverters(MapTypeConverter::class)
class RateDto(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "rates") val rates: Map<String, Double>,
)

fun RateDto.toCurrency(): Currency {
    return Currency(
        rates = rates,
        date = date,
        base = name
    )
}

class MapTypeConverter {
    @TypeConverter
    fun fromString(value: String): Map<String, Double> {
        val mapType = object : TypeToken<Map<String, Double>>() {}.type
        return Gson().fromJson(value, mapType)
    }

    @TypeConverter
    fun fromMap(map: Map<String, Double>): String {
        return Gson().toJson(map)
    }
}