package msi.paria.data.main.dao

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import msi.paria.data.dto.db.TransactionDto
import msi.paria.data.repository.db.dao.TransactionDao
import org.junit.Assert.assertEquals
import org.junit.Test

class TransactionDaoTest {

    @Test
    fun testGetTransactions() {
        val transactions = mockk<List<TransactionDto>>(relaxed = true)

        val transactionDao = mockk<TransactionDao>(relaxed = true)
        every { transactionDao.getTransactions() } returns transactions

        val result = transactionDao.getTransactions()

        assertEquals(transactions, result)
    }

    @Test
    fun testInsert() {
        val transaction = mockk<TransactionDto>(relaxed = true)

        val transactionDao = mockk<TransactionDao>(relaxed = true)

        transactionDao.insert(transaction)

        verify { transactionDao.insert(transaction) }
    }

    @Test
    fun testInsertAll() {
        val transactions = mockk<List<TransactionDto>>(relaxed = true)

        val transactionDao = mockk<TransactionDao>(relaxed = true)

        transactionDao.insertAll(transactions)

        verify { transactionDao.insertAll(transactions) }
    }

    @Test
    fun testDelete() {
        val transaction = mockk<TransactionDto>(relaxed = true)

        val transactionDao = mockk<TransactionDao>(relaxed = true)

        transactionDao.delete(transaction)

        verify { transactionDao.delete(transaction) }
    }
}