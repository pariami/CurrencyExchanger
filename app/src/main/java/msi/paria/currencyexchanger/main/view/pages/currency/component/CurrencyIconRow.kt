package msi.paria.currencyexchanger.main.view.pages.currency.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun CurrencyIconRow(
    text: String,
    @DrawableRes iconRes: Int,
    iconBack: Color,
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
    ) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(15.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.padding(top = 6.dp)
    ) {
        IconButton(
            onClick = { },
            modifier = Modifier
                .padding(4.dp)
                .clip(CircleShape)
                .background(color = iconBack)
        ) {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = text,
                tint = Color.Unspecified
            )
        }

        Text(text = text, modifier = Modifier.padding(start = 4.dp))

        content()
    }
}