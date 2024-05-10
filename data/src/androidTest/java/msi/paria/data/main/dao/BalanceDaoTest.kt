package msi.paria.data.main.dao

import android.content.Context
import androidx.room.Room.inMemoryDatabaseBuilder
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.runBlocking
import msi.paria.data.dto.db.BalanceDto
import msi.paria.data.repository.db.TransactionDB
import msi.paria.data.repository.db.dao.BalanceDao
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class BalanceDaoTest {

    private lateinit var balanceDao: BalanceDao
    private lateinit var db: TransactionDB

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = inMemoryDatabaseBuilder(context, TransactionDB::class.java)
            .allowMainThreadQueries()
            .build()
        balanceDao = db.balanceDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetBalance() = runBlocking {
        val balanceDto = BalanceDto(id = 1, name = "USD", amount = 100.0)
        balanceDao.insert(balanceDto)
        val loaded = balanceDao.getBalanceById("USD")
        assertEquals(balanceDto.name, loaded.name)
        assertEquals(balanceDto.amount, loaded.amount, 0.001)
    }

    @Test
    @Throws(Exception::class)
    fun getAllBalance() = runBlocking {
        val balance1 = BalanceDto(name = "USD", amount = 100.0)
        val balance2 = BalanceDto(name = "EUR", amount = 200.0)
        balanceDao.insertAll(listOf(balance1, balance2))
        val allBalances = balanceDao.getAllBalance()
        assertEquals(2, allBalances.size)
    }
}