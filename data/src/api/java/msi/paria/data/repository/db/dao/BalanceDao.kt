package msi.paria.data.repository.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import msi.paria.data.dto.db.BalanceDto

@Dao
interface BalanceDao {
    @Query("SELECT * FROM `balance` order by id asc")
    fun getAllBalance(): List<BalanceDto>

    @Query("SELECT * FROM balance WHERE name = :name limit 1")
    fun getBalanceById(name:String): BalanceDto

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(balanceDto: BalanceDto)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(balances: List<BalanceDto>)
}