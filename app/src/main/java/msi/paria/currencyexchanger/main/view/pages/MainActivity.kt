package msi.paria.currencyexchanger.main.view.pages

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import msi.paria.currencyexchanger.main.view.pages.currency.CurrencyScreen
import msi.paria.currencyexchanger.main.theme.CurrencyExchangerTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CurrencyExchangerTheme {
                CurrencyScreen()
            }
        }
    }
}