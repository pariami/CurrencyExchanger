package msi.paria.currencyexchanger.main.view.pages.currency.components

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import msi.paria.currencyexchanger.main.view.pages.currency.component.CurrencyItem
import org.junit.Rule
import org.junit.Test

class CurrencyItemUiTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun givenCurrencyItem_whenRendered_thenDisplaysCurrency() {
        val currency = "USD"

        composeTestRule.setContent {
            CurrencyItem(currency = currency)
        }

        composeTestRule.onNodeWithText(currency).assertExists()
    }
}