package msi.paria.data.repository.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import msi.paria.data.dto.db.RateDto

@Dao
interface RateDao {
    @Query("SELECT * FROM rate WHERE name = :name limit 1")
    fun getRateByName(name: String): RateDto

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(rateDto: RateDto)
}