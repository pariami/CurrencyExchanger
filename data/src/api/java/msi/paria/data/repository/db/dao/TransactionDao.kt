package msi.paria.data.repository.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import msi.paria.data.dto.db.TransactionDto

@Dao
interface TransactionDao {
    @Query("SELECT * FROM `transaction` order by id asc")
    fun getTransactions(): List<TransactionDto>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(transaction: TransactionDto)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(transactions: List<TransactionDto>)

    @Delete
    fun delete(transaction: TransactionDto)
}