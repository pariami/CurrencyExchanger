package msi.paria.currencyexchanger.main.view.pages.currency.component

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue

@Composable
fun CurrencyRow(
    text: String,
    iconRes: Int,
    iconBack: Color,
    amount: TextFieldValue,
    focusable: Boolean = false,
    rates: List<String>,
    selectedCurrencyIndex: Int,
    onAmountValueEntered: (String) -> Unit,
    onCurrencySelected: (Pair<Int, String>) -> Unit
) {
    CurrencyIconRow(
        text = text,
        iconRes = iconRes,
        iconBack = iconBack,
        modifier = Modifier.fillMaxWidth()
    ) {
        CurrencyTextField(
            value = amount.text,
            modifier = Modifier
                .weight(1f)
                .focusable(focusable),
            onAmountValueChanged = { onAmountValueEntered(it) }
        )

        CurrencySpinner(
            currencyCodes = rates,
            selectedCurrency = rates[selectedCurrencyIndex],
            onCurrencySelected = { index, currency ->
                onCurrencySelected(index to currency)
            }
        )
    }
}