package msi.paria.currencyexchanger.main.view.pages.currency

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import msi.paria.currencyexchanger.main.state.CurrencyScreenState
import msi.paria.currencyexchanger.main.view.pages.MainViewModel
import msi.paria.currencyexchanger.main.view.pages.contract.CurrencyScreenEffect
import msi.paria.currencyexchanger.main.view.pages.contract.CurrencyScreenEvent
import msi.paria.domain.model.Resource

@Composable
fun CurrencyScreen() {
    val viewModel: MainViewModel = viewModel()

    val state: CurrencyScreenState by viewModel.state.collectAsState()

    val showDialog = remember {
        mutableStateOf(false)
    }
    val submitButtonEnabled = remember {
        mutableStateOf(true)
    }

    LaunchedEffect(viewModel) {
        viewModel.effectFlow.collect { event ->
            when (event) {
                is CurrencyScreenEffect.OnRatesReceived -> {
                    viewModel.onEvent(CurrencyScreenEvent.OnCurrencyRateUpdated)
                }

                CurrencyScreenEffect.ShowResultDialog -> {
                    if (state.message.isNotEmpty()) {
                        showDialog.value = true
                    }
                }

                CurrencyScreenEffect.ChangeSubmitButtonState -> {
                    submitButtonEnabled.value = state.canConvert
                }
            }
        }
    }

    CurrencyContent(
        onAmountValueEntered = { viewModel.onEvent(CurrencyScreenEvent.OnAmountValueEntered(it)) },
        onFromCurrencySelected = {
            viewModel.onEvent(
                CurrencyScreenEvent.OnFromCurrencySelected(it)
            )
        },
        onToCurrencySelected = { viewModel.onEvent(CurrencyScreenEvent.OnToCurrencySelected(it)) },
        onSubmitButtonClicked = { viewModel.onEvent(CurrencyScreenEvent.OnSubmitButtonClicked) },
        rates = state.rates.keys.toList(),
        message = state.message,
        balances = state.balances,
        convertedAmount = state.convertedAmount.toString(),
        showDialog = showDialog,
        submitButtonEnabled = submitButtonEnabled.value,
        isLoading = state.currencyResponse is Resource.Loading,
        modifier = Modifier.padding(16.dp)
    )

}