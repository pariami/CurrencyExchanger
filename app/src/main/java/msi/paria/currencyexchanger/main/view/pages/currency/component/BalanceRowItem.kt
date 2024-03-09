package msi.paria.currencyexchanger.main.view.pages.currency.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import msi.paria.domain.model.Balance

@Composable
fun BalanceRowItem(modifier:Modifier = Modifier,
                   balance: Balance,
                   color: Color = Color.Black
){
    Text(
        text = "${balance.amount} ${balance.name} ",
        modifier = Modifier.padding(8.dp),
        style = TextStyle(color = Color.Black)
    )
}