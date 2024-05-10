package msi.paria.data.main.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.runBlocking
import msi.paria.data.dto.db.RateDto
import msi.paria.data.repository.db.TransactionDB
import msi.paria.data.repository.db.dao.RateDao
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class RateDaoTest {

    private lateinit var rateDao: RateDao
    private lateinit var db: TransactionDB

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, TransactionDB::class.java)
            .allowMainThreadQueries()
            .build()
        rateDao = db.rateDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetRate() = runBlocking {
        val rateDto = RateDto(name = "USD", date = "2024-05-09", rates = mapOf("EUR" to 0.85))
        rateDao.insert(rateDto)
        val loaded = rateDao.getRateByName("USD")
        assertEquals(rateDto.name, loaded.name)
        assertEquals(rateDto.rates, loaded.rates)
    }
}