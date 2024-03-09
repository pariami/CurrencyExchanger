import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ConvertCurrencyUseCaseTest {

    private lateinit var repository: ExchangeRateRepository
    private lateinit var useCase: ConvertCurrencyUseCase

    @Before
    fun setUp() {
        repository = mockk()
        useCase = ConvertCurrencyUseCase(repository)
    }

    @Test
    fun `test successful currency conversion`() = runBlocking {
        val transaction = Transaction(100.0,"EUR", "USD"  )
        val exchangeRates = listOf(ExchangeRate( "EUR","USD", 0.85))
        coEvery { repository.getExchangeRates() } returns exchangeRates
        coEvery { repository.updateBalance(any(), any()) } returns Unit

        val result = useCase(transaction)

        assertEquals(85.0, result, 0.001)
    }

/*    @Test(expected = InvalidExchangeRateException::class)
    fun `test exchange rate not found`() = runBlocking {
        val transaction = Transaction(100.0, "EUR", "USD" )
        val exchangeRates = emptyList<ExchangeRate>()
        coEvery { repository.getExchangeRates() } returns exchangeRates

        useCase(transaction)
    }*/

    // Add more test cases as needed...
}
