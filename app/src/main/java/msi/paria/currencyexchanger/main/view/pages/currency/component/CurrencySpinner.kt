package msi.paria.currencyexchanger.main.view.pages.currency.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import msi.paria.currencyexchanger.util.TestTags

@Composable
fun CurrencySpinner(
    currencyCodes: List<String>, selectedCurrency: String, onCurrencySelected: (Int, String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.End
    ) {
        Text(text = selectedCurrency,
            modifier = Modifier
                .padding(16.dp)
                .clickable { expanded = true }
                .testTag(TestTags.dropDownTag))

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            currencyCodes.forEachIndexed { index, currency ->
                DropdownMenuItem(onClick = {
                    onCurrencySelected(index, currency)
                    expanded = false
                }, text = { CurrencyItem(currency) })
            }
        }
    }
}