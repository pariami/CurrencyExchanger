package msi.paria.currencyexchanger.main.view.pages.currency.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CurrencyItem(currency: String) {
    Column(Modifier.padding(8.dp)) {
        Text(text = currency)
    }
}