package msi.paria.currencyexchanger.main.view.pages.currency.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import msi.paria.domain.model.Balance

@Composable
fun BalanceSection(
    text: String,
    balancesItems: List<Balance>,
    modifier: Modifier = Modifier,
    color: Color = Color.Black
) {
    Text(
        text = text,
        modifier = modifier,
        style = TextStyle(color = color),
    )
    LazyRow(modifier = Modifier.padding(top = 8.dp)) {
        items(balancesItems) { balance ->
            BalanceRowItem(
                modifier = Modifier.padding(8.dp),
                balance = balance,
                color = Color.Black
            )
        }
    }
}