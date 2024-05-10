package msi.paria.currencyexchanger.main.view.pages

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import msi.paria.currencyexchanger.main.state.CurrencyScreenState
import msi.paria.currencyexchanger.main.view.pages.contract.CurrencyScreenEffect
import msi.paria.currencyexchanger.main.view.pages.contract.CurrencyScreenEvent
import msi.paria.domain.model.Balance
import msi.paria.domain.model.Currency
import msi.paria.domain.model.Resource
import msi.paria.domain.model.Transaction
import msi.paria.domain.usecase.InternalUseCse
import msi.paria.domain.usecase.UseCase
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCase: UseCase,
    private val internalUseCase: InternalUseCse,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _state = MutableStateFlow(CurrencyScreenState())
    val state: StateFlow<CurrencyScreenState> = _state.asStateFlow()

    private val _effectFlow = MutableSharedFlow<CurrencyScreenEffect>(replay = 1)
    val effectFlow = _effectFlow.asSharedFlow()

    init {
        initialize()
    }

    private fun initialize() {
        _state.update { it.copy(currencyResponse = Resource.Loading()) }
        startFetchingData()
        getAllBalance()
    }

    fun onEvent(event: CurrencyScreenEvent) {
        when (event) {
            is CurrencyScreenEvent.OnFromCurrencySelected -> {
                updateFromCurrency(event.currency)
                checkBalanceAndUpdate(event.currency)
            }

            is CurrencyScreenEvent.OnSubmitButtonClicked -> fetchCommission()
            is CurrencyScreenEvent.OnToCurrencySelected -> updateToCurrency(event.currency)

            is CurrencyScreenEvent.OnAmountValueEntered -> {
                if (event.amount.isNotEmpty()) {
                    val enteredValue = persianToEnglishNumber(event.amount)
                    updateAmount(enteredValue)
                    checkBalanceAndUpdate(_state.value.fromCurrency)
                }
            }

            is CurrencyScreenEvent.OnCurrencyRateUpdated -> convert()
        }
    }

    private fun updateFromCurrency(currency: String) {
        _state.update { it.copy(fromCurrency = currency) }
    }

    private fun updateToCurrency(currency: String) {
        _state.update { it.copy(toCurrency = currency) }
    }

    private fun updateAmount(amount: String) {
        _state.update { it.copy(amount = amount) }
    }

    private fun checkBalanceAndUpdate(currency: String) {
        checkBalance(currency)
        convert()
    }

    private fun checkBalance(currency: String) {
        viewModelScope.launch(dispatcher) {
            val canConvert = internalUseCase.checkBalance(currency, _state.value.amount.toDouble())
            val message = if (!canConvert) "This Currency isn't enough for convert" else ""
            _state.update { it.copy(canConvert = canConvert, message = message) }
            _effectFlow.tryEmit(CurrencyScreenEffect.ChangeSubmitButtonState)
        }
    }

    fun getAllBalance() {
        viewModelScope.launch(dispatcher) {
            useCase.getAllBalance().collect { balances ->
                if (balances.isEmpty()) {
                    useCase.insertBalance(Balance(1, "USD", 100.0))
                    getAllBalance()
                } else {
                    _state.update { it.copy(balances = balances) }
                }
            }
        }
    }

    private fun startFetchingData() {
        viewModelScope.launch {
            flow {
                while (true) {
                    emit(Unit)
                    delay(5000)
                }
            }.collect {
                useCase.getExchangeRates(_state.value.fromCurrency).collectLatest { resource ->
                    _state.update { it.copy(currencyResponse = resource) }
                    handleExchangeRatesResponse(resource)
                }
            }
        }
    }

    private fun handleExchangeRatesResponse(resource: Resource<Currency>) {
        when (resource) {
            is Resource.Error -> {
                _state.update { it.copy(message = resource.message ?: "Unknown error") }
                //_effectFlow.tryEmit(CurrencyScreenEffect.ShowResultDialog)
            }

            is Resource.Success -> {
                val rates = resource.data?.rates ?: emptyMap()
                _state.update { it.copy(rates = rates) }
                _effectFlow.tryEmit(CurrencyScreenEffect.OnRatesReceived)
            }

            else -> {}
        }
    }

    fun fetchCommission() {
        viewModelScope.launch(dispatcher) {
            val commissionFee = internalUseCase.getCommissionUseCase()
            _state.update { it.copy(commissionFee = commissionFee) }
            submit()
        }
    }

    private fun convert() {
        viewModelScope.launch(dispatcher) {
            val amountStr = _state.value.amount
            val fromCurrency = _state.value.fromCurrency
            val toCurrency = _state.value.toCurrency
            val ratesResponse = _state.value.currencyResponse

            val convertedAmount = internalUseCase.currencyConversion(
                ratesResponse,
                amountStr,
                fromCurrency,
                toCurrency
            )
            _state.update {
                it.copy(
                    convertedAmount = convertedAmount,
                    message = "$amountStr $fromCurrency = $convertedAmount $toCurrency, commissionFee: ${_state.value.commissionFee}"
                )
            }
        }
    }

    private fun submit() {
        if (_state.value.canConvert) {
            viewModelScope.launch(dispatcher) {
                val fromCurrency = _state.value.fromCurrency
                val toCurrency = _state.value.toCurrency
                val amount = _state.value.amount.toDouble()
                val convertedAmount = _state.value.convertedAmount

                transferFunds(fromCurrency, toCurrency, amount, convertedAmount)
                insertTransaction()
            }
        }
        _effectFlow.tryEmit(CurrencyScreenEffect.ShowResultDialog)
    }

    private fun transferFunds(
        fromCurrency: String,
        toCurrency: String,
        fromAmount: Double,
        convertedCurrency: Double
    ) {
        viewModelScope.launch(dispatcher) {
            internalUseCase.transferFunds(fromCurrency, toCurrency, fromAmount, convertedCurrency)
            getAllBalance()
        }
    }

    private suspend fun insertTransaction() {
        val transaction = Transaction(
            amount = _state.value.amount.toDouble(),
            fromCurrency = _state.value.fromCurrency,
            toCurrency = _state.value.toCurrency,
            convertedAmount = _state.value.convertedAmount,
            commissionFee = (_state.value.commissionFee * _state.value.amount.toDouble() * 100) / 100
        )
        useCase.insertTransaction(transaction)
    }

    fun persianToEnglishNumber(persianNumber: String): String {
        val persianDigits = listOf("۰", "۱", "۲", "۳", "۴", "۵", "۶", "۷", "۸", "۹")
        val englishDigits = listOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9")

        var result = persianNumber
        for (i in persianDigits.indices) {
            result = result.replace(persianDigits[i], englishDigits[i])
        }
        return result
    }
}