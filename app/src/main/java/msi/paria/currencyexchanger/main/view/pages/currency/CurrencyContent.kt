package msi.paria.currencyexchanger.main.view.pages.currency

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import msi.paria.currencyexchanger.R
import msi.paria.currencyexchanger.main.view.pages.currency.component.BalanceSection
import msi.paria.currencyexchanger.main.view.pages.currency.component.CurrencyRow
import msi.paria.currencyexchanger.main.view.pages.currency.component.PopUpDialog
import msi.paria.currencyexchanger.main.view.pages.currency.component.SubmitButton
import msi.paria.domain.model.Balance

@Composable
fun CurrencyContent(
    onAmountValueEntered: (String) -> Unit,
    onFromCurrencySelected: (currency: String) -> Unit,
    onToCurrencySelected: (currency: String) -> Unit,
    onSubmitButtonClicked: () -> Unit,
    rates: List<String> = emptyList(),
    message: String,
    balances: List<Balance>,
    convertedAmount: String,
    showDialog: MutableState<Boolean>,
    submitButtonEnabled: Boolean,
    isLoading: Boolean,
    modifier: Modifier = Modifier
) {
    var selectedFromCurrencyIndex by remember { mutableIntStateOf(0) }
    var selectedToCurrencyIndex by remember { mutableIntStateOf(0) }
    var inputAmount by remember { mutableStateOf(TextFieldValue("0.0")) }

    Box(modifier = modifier.fillMaxSize()) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = modifier
                    .align(Alignment.Center)
            )
        } else {
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(20.dp),
                modifier = modifier
            ) {
                BalanceSection(
                    text = "My Balances",
                    balancesItems = balances,
                )

                CurrencyRow(
                    text = "Sell",
                    iconRes = R.drawable.ic_sell,
                    iconBack = Color.Red,
                    amount = inputAmount,
                    focusable = true,
                    rates = rates,
                    selectedCurrencyIndex = selectedFromCurrencyIndex,
                    onAmountValueEntered = {
                        onAmountValueEntered(it)
                        inputAmount = TextFieldValue(it)
                    },
                    onCurrencySelected = {
                        onFromCurrencySelected(it.second)
                        selectedFromCurrencyIndex = it.first
                    }
                )

                CurrencyRow(
                    text = "Receive",
                    iconRes = R.drawable.ic_receive,
                    iconBack = Color.Green,
                    amount = TextFieldValue(convertedAmount),
                    rates = rates,
                    selectedCurrencyIndex = selectedToCurrencyIndex,
                    onAmountValueEntered = {},
                    onCurrencySelected = {
                        onToCurrencySelected(it.second)
                        selectedToCurrencyIndex = it.first
                    }
                )

                SubmitButton(
                    text = "Submit",
                    modifier = Modifier
                        .padding(horizontal = 32.dp)
                        .fillMaxWidth(),
                    onSubmitButtonClicked = onSubmitButtonClicked,
                    submitButtonEnabled = submitButtonEnabled
                )

                PopUpDialog(
                    message = message,
                    onDismissDialog = { showDialog.value = false },
                    onConfirmDialog = { showDialog.value = false },
                    showDialog = showDialog.value
                )
            }
        }
    }
}