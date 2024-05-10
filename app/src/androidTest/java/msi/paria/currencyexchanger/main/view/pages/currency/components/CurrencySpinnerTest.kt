package msi.paria.currencyexchanger.main.view.pages.currency.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import msi.paria.currencyexchanger.main.view.pages.currency.component.CurrencySpinner
import org.junit.Rule
import org.junit.Test

class CurrencySpinnerTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun givenCurrencySpinner_whenRendered_thenDisplaysSelectedCurrencyAndOptions() {
        val currencyCodes = listOf("USD", "EUR")
        val selectedCurrency = "USD"

        var selectedCurrencyIndex = 0
        var selectedCurrencyResult = ""

        composeTestRule.setContent {
            MaterialTheme {
                CurrencySpinner(
                    currencyCodes = currencyCodes,
                    selectedCurrency = selectedCurrency
                ) { index, currency ->
                    selectedCurrencyIndex = index
                    selectedCurrencyResult = currency
                }
            }
        }

        composeTestRule.onNodeWithText(selectedCurrency).assertExists()

        composeTestRule.onNodeWithText(selectedCurrency).performClick()

        currencyCodes.forEachIndexed { index, currency ->
            if (currency != selectedCurrency) {
                composeTestRule.onNodeWithText(currency).assertExists().performClick()
                assert(selectedCurrencyIndex == index)
                assert(selectedCurrencyResult == currency)
            }
        }
    }
}