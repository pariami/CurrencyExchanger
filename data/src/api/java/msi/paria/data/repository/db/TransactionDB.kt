package msi.paria.data.repository.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import msi.paria.data.dto.db.BalanceDto
import msi.paria.data.dto.db.TransactionDto
import msi.paria.data.repository.db.dao.BalanceDao
import msi.paria.data.repository.db.dao.TransactionDao

@Database(entities = [TransactionDto::class, BalanceDto::class], version = 1)
abstract class TransactionDB : RoomDatabase() {
    companion object {
        @Volatile
        private var INSTANCE: TransactionDB? = null

        fun getDatabase(context: Context): TransactionDB {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TransactionDB::class.java,
                    "transactions_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

    abstract fun transactionDao(): TransactionDao
    abstract fun balanceDao(): BalanceDao
}