@file:Suppress("DEPRECATION")

import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import msi.paria.currencyexchanger.main.view.pages.MainViewModel
import msi.paria.currencyexchanger.main.view.pages.contract.CurrencyScreenEvent
import msi.paria.domain.model.Balance
import msi.paria.domain.usecase.InternalUseCse
import msi.paria.domain.usecase.UseCase
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class MainViewModelTest {

    private lateinit var viewModel: MainViewModel
    private val usecase: UseCase = mockk()
    private val internalUseCase: InternalUseCse = mockk()
    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        MockKAnnotations.init(this)
        viewModel = MainViewModel(usecase, internalUseCase, testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `given valid currencies and amounts, when invoking transferFunds, then balances should be updated accordingly`() =
        runBlocking {
            // Given
            val fromCurrency = "USD"
            val toCurrency = "EUR"
            val fromAmount = 100.0
            val convertedCurrency = 80.0

            val fromBalance = Balance(1, fromCurrency, 100.0)
            val toBalance = Balance(2, toCurrency, 50.0)

            coEvery { internalUseCase.checkBalance(fromCurrency, fromAmount) } returns true
            coEvery {
                internalUseCase.currencyConversion(
                    any(),
                    any(),
                    any(),
                    any()
                )
            } returns convertedCurrency
            coEvery {
                internalUseCase.transferFunds(
                    fromCurrency,
                    toCurrency,
                    fromAmount,
                    convertedCurrency
                )
            } returns Unit
            coEvery { usecase.insertTransaction(any()) } returns Unit
            coEvery { usecase.getAllBalance() } returns flowOf(listOf(fromBalance, toBalance))

            viewModel.onEvent(CurrencyScreenEvent.OnFromCurrencySelected(fromCurrency))
            viewModel.onEvent(CurrencyScreenEvent.OnToCurrencySelected(toCurrency))
            viewModel.onEvent(CurrencyScreenEvent.OnAmountValueEntered(fromAmount.toString()))
            viewModel.onEvent(CurrencyScreenEvent.OnSubmitButtonClicked)
        }

    @Test
    fun `given insufficient balance, when invoking transferFunds, then show error message`() =
        runBlocking {
            val fromCurrency = "USD"
            val toCurrency = "EUR"
            val fromAmount = 100.0

            coEvery { internalUseCase.checkBalance(fromCurrency, fromAmount) } returns false

            viewModel.onEvent(CurrencyScreenEvent.OnFromCurrencySelected(fromCurrency))
            viewModel.onEvent(CurrencyScreenEvent.OnToCurrencySelected(toCurrency))
            viewModel.onEvent(CurrencyScreenEvent.OnAmountValueEntered(fromAmount.toString()))
            viewModel.onEvent(CurrencyScreenEvent.OnSubmitButtonClicked)
        }

    @Test
    fun `given valid currencies and amounts, when invoking submit without sufficient balance, then show error message`() =
        runBlocking {
            // Given
            val fromCurrency = "USD"
            val toCurrency = "EUR"
            val fromAmount = 100.0

            coEvery { internalUseCase.checkBalance(fromCurrency, fromAmount) } returns false

            viewModel.onEvent(CurrencyScreenEvent.OnFromCurrencySelected(fromCurrency))
            viewModel.onEvent(CurrencyScreenEvent.OnToCurrencySelected(toCurrency))
            viewModel.onEvent(CurrencyScreenEvent.OnAmountValueEntered(fromAmount.toString()))
            viewModel.onEvent(CurrencyScreenEvent.OnSubmitButtonClicked)
        }

    @Test
    fun `given valid currencies and amounts, when invoking submit with sufficient balance, then perform transfer and insert transaction`() =
        runBlocking {
            val fromCurrency = "USD"
            val toCurrency = "EUR"
            val fromAmount = 100.0
            val convertedCurrency = 80.0

            val fromBalance = Balance(1, fromCurrency, 100.0)
            val toBalance = Balance(2, toCurrency, 50.0)

            coEvery { internalUseCase.checkBalance(fromCurrency, fromAmount) } returns true
            coEvery {
                internalUseCase.currencyConversion(
                    any(),
                    any(),
                    any(),
                    any()
                )
            } returns convertedCurrency
            coEvery {
                internalUseCase.transferFunds(
                    fromCurrency,
                    toCurrency,
                    fromAmount,
                    convertedCurrency
                )
            } returns Unit
            coEvery { usecase.insertTransaction(any()) } returns Unit
            coEvery { usecase.getAllBalance() } returns flowOf(listOf(fromBalance, toBalance))

            viewModel.onEvent(CurrencyScreenEvent.OnFromCurrencySelected(fromCurrency))
            viewModel.onEvent(CurrencyScreenEvent.OnToCurrencySelected(toCurrency))
            viewModel.onEvent(CurrencyScreenEvent.OnAmountValueEntered(fromAmount.toString()))
            viewModel.onEvent(CurrencyScreenEvent.OnSubmitButtonClicked)
        }

    @Test
    fun `given valid currencies, when invoking fetchCommission, then update commission fee`() =
        runBlocking {
            // Given
            val commissionFee = 0.5

            coEvery { internalUseCase.getCommissionUseCase() } returns commissionFee

            viewModel.fetchCommission()
        }

    @Test
    fun `given valid currencies and amounts, when invoking getAllBalance, then update balances`() =
        runBlocking {
            val fromCurrency = "USD"
            val toCurrency = "EUR"

            val fromBalance = Balance(1, fromCurrency, 100.0)
            val toBalance = Balance(2, toCurrency, 50.0)

            coEvery { usecase.getAllBalance() } returns flowOf(listOf(fromBalance, toBalance))

            viewModel.getAllBalance()
        }
}