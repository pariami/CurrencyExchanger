package msi.paria.currencyexchanger.main.view.pages.currency

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import msi.paria.currencyexchanger.R
import msi.paria.currencyexchanger.main.view.pages.currency.component.BalanceSection
import msi.paria.currencyexchanger.main.view.pages.currency.component.CurrencyIconRow
import msi.paria.currencyexchanger.main.view.pages.currency.component.CurrencySpinner
import msi.paria.currencyexchanger.main.view.pages.currency.component.CurrencyTextField
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

    var selectedFromCurrencyIndex by remember { mutableStateOf(0) }
    var selectedToCurrencyIndex by remember { mutableStateOf(0) }

    var inputAmount by remember { mutableStateOf(TextFieldValue("0.0")) }

    Box(modifier = Modifier.fillMaxSize()) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .padding(10.dp)
                    .align(Alignment.Center)
            )
        } else {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp),
                modifier = modifier
            ) {
                BalanceSection(
                    text = "My Balances",
                    balancesItems = balances,
                    modifier = Modifier.padding(8.dp)
                )

                CurrencyIconRow(
                    text = "Sell",
                    iconRes = R.drawable.ic_sell,
                    iconBack = Color.Red,
                    modifier = Modifier.fillMaxWidth()
                ) {

                    CurrencyTextField(inputAmount.text,
                        modifier = Modifier
                            .padding(6.dp)
                            .weight(1f),
                        onAmountValueChanged = {
                            inputAmount = TextFieldValue(it)
                            onAmountValueEntered(it)
                        })

                    CurrencySpinner(currencyCodes = rates,
                        selectedCurrency = rates[selectedFromCurrencyIndex],
                        onCurrencySelected = { index, currency ->
                            onFromCurrencySelected(currency)
                            selectedFromCurrencyIndex = index
                        })
                }

                CurrencyIconRow(
                    text = "Receive",
                    iconRes = R.drawable.ic_receive,
                    iconBack = Color.Green,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = convertedAmount, modifier = Modifier.padding(4.dp).weight(1f),)

                    CurrencySpinner(
                        currencyCodes = rates,
                        selectedCurrency = rates[selectedToCurrencyIndex],
                        onCurrencySelected = { index, currency ->
                            onToCurrencySelected(currency)
                            selectedToCurrencyIndex = index
                        },
                    )
                }

                SubmitButton(
                    text = "Submit",
                    modifier = Modifier
                        .padding(horizontal = 32.dp)
                        .fillMaxWidth(),
                    onSubmitButtonClicked = { onSubmitButtonClicked() },
                    submitButtonEnabled = submitButtonEnabled
                )

                PopUpDialog(message = message,
                    onDismissDialog = { showDialog.value = false },
                    onConfirmDialog = { showDialog.value = false },
                    showDialog = showDialog.value
                )
            }
        }
    }
}
