package msi.paria.currencyexchanger.main.view.pages

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
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
import msi.paria.domain.usecase.UseCase
import msi.paria.currencyexchanger.main.view.pages.contract.CurrencyScreenEffect
import msi.paria.currencyexchanger.main.view.pages.currency.CurrencyEvent
import msi.paria.currencyexchanger.main.view.pages.contract.CurrencyScreenEvent
import msi.paria.domain.model.Balance
import msi.paria.domain.model.Resource
import msi.paria.domain.model.Transaction
import javax.inject.Inject
import kotlin.math.round

@HiltViewModel
class MainViewModel @Inject constructor(
    private val usecase: UseCase,
    private val dispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _conversion = MutableStateFlow<CurrencyEvent>(CurrencyEvent.Empty)

    private val _state = MutableStateFlow(CurrencyScreenState())
    val state: StateFlow<CurrencyScreenState> = _state.asStateFlow()

    private val _effectFlow = MutableSharedFlow<CurrencyScreenEffect>(replay = 1)
    val effectFlow = _effectFlow.asSharedFlow()

    init {
        _state.update { state ->
            state.copy(currencyResponse = Resource.Loading())
        }
        startFetchingData()
        getAllBalance()
    }

    fun onEvent(event: CurrencyScreenEvent) {
        when (event) {
            is CurrencyScreenEvent.OnFromCurrencySelected -> {
                _state.update { state -> state.copy(fromCurrency = event.currency) }
                checkBalance(event.currency)
            }

            CurrencyScreenEvent.OnSubmitButtonClicked -> {
                getTransactions()
            }

            is CurrencyScreenEvent.OnToCurrencySelected -> {
                _state.update { state -> state.copy(toCurrency = event.currency) }
            }

            is CurrencyScreenEvent.OnAmountValueEntered -> {
                _state.update { state -> state.copy(amount = event.amount) }
                checkBalance(_state.value.fromCurrency)
                convert(
                    _state.value.amount, _state.value.fromCurrency, _state.value.toCurrency
                )
            }

            CurrencyScreenEvent.OnCurrencyRateUpdated -> {
                convert(
                    _state.value.amount, _state.value.fromCurrency, _state.value.toCurrency
                )
            }
        }
    }

    private fun checkBalance(currency:String){
        viewModelScope.launch(dispatcher) {
            val balance = usecase.getBalanceByName(currency)
            val canConvert = (balance?.amount ?: 0.0) >= _state.value.amount.toDouble()
            _state.update { state -> state.copy(canConvert = canConvert) }

            if (!canConvert) {
                _state.update { state -> state.copy(message = "This Currency isn't enough for convert") }
                _effectFlow.tryEmit(CurrencyScreenEffect.ShowResultDialog)
            } else {
                _state.update { state -> state.copy(message = "") }
                _effectFlow.tryEmit(CurrencyScreenEffect.ShowResultDialog)
            }
        }
    }
    private fun getAllBalance() {
        viewModelScope.launch(dispatcher) {
            usecase.getAllBalance().collect { balances ->
                _state.update { state ->
                    state.copy(balances = balances)
                }
                //_effectFlow.emit(CurrencyScreenEffect.OnBalancesReceived)
            }
        }
    }

    private fun startFetchingData() {
        viewModelScope.launch {
            flow {
                while (true) {
                    emit(Unit)
                    delay(5000) // delay for 5 seconds
                }
            }.collect {
                usecase.getExchangeRates(_state.value.fromCurrency).collectLatest { resource ->
                    _state.update { state ->
                        state.copy(currencyResponse = resource)
                    }
                    when (resource) {
                        is Resource.Error -> _conversion.value =
                            CurrencyEvent.Failure(resource.message!!)

                        is Resource.Success -> {
                            val rates = resource.data!!.rates
                            _state.update { state ->
                                state.copy(rates = rates)
                            }
                            _effectFlow.emit(CurrencyScreenEffect.OnRatesReceived)
                        }

                        else -> {
                            _conversion.value = CurrencyEvent.Failure("Unexpected error")
                        }
                    }
                }
            }
        }
    }
    private fun getTransactions() {
        viewModelScope.launch(dispatcher) {
            val transactions = usecase.getTransactions()
            val commissionFee = if (transactions.size < 5) 0.0 else 0.7

            _state.update { state -> state.copy(commissionFee = commissionFee) }

            /*convert(
                _state.value.amount, _state.value.fromCurrency, _state.value.toCurrency
            )*/
            submit()
        }
    }

    fun convert(amountStr: String, fromCurrency: String, toCurrency: String) {
        val fromAmount = amountStr.toFloatOrNull() ?: return

        viewModelScope.launch(dispatcher) {
            val ratesResponse = _state.value.currencyResponse
            if (ratesResponse is Resource.Success) {
                val rates = ratesResponse.data!!.rates
                val fromRate: Double = rates[fromCurrency] ?: 0.0
                val toRate: Double = rates[toCurrency] ?: 0.0

                val conversionRate = toRate / fromRate
                val convertedCurrency = round(fromAmount * conversionRate * 100) / 100

                _state.update { state ->
                    state.copy(
                        convertedAmount = convertedCurrency,
                        message = "$fromAmount $fromCurrency = $convertedCurrency $toCurrency // ${(_state.value.commissionFee * _state.value.amount.toDouble() * 100) / 100} $fromCurrency commission fee"
                    )
                }
               // _effectFlow.emit(CurrencyScreenEffect.OnConvertedAmountChanged)
            }
        }
    }

    private suspend fun submit(){
        getBalanceByName(_state.value.fromCurrency, _state.value.toCurrency, _state.value.amount.toDouble(), _state.value.convertedAmount)
        insertTransaction()
        _effectFlow.emit(CurrencyScreenEffect.ShowResultDialog)
    }

    private fun getBalanceByName(
        fromCurrency: String, toCurrency: String, fromAmount: Double, convertedCurrency: Double
    ) {
        viewModelScope.launch(dispatcher) {
            val fromBalance =
                usecase.getBalanceByName(fromCurrency) ?: Balance(0, fromCurrency, 0.0)
            fromBalance.amount -= fromAmount
            usecase.insertBalance(fromBalance)

            val toBalance = usecase.getBalanceByName(toCurrency) ?: Balance(0, toCurrency, 0.0)
            toBalance.amount += convertedCurrency
            usecase.insertBalance(toBalance)

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
        usecase.insertTransaction(transaction)
    }
}