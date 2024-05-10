package msi.paria.currencyexchanger.main.view.pages.currency.components

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import msi.paria.currencyexchanger.main.view.pages.currency.component.BalanceRowItem
import msi.paria.domain.model.Balance
import org.junit.Rule
import org.junit.Test

class BalanceRowItemTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun givenBalanceRowItem_whenRendered_thenDisplaysBalanceAmountAndCurrency() {
        val balance = Balance(id = 1, name = "USD", amount = 100.0)
        val color = Color.Red

        composeTestRule.setContent {
            BalanceRowItem(
                modifier = Modifier,
                balance = balance,
                color = color
            )
        }
        composeTestRule.onNodeWithText("100.0 USD", useUnmergedTree = true).assertExists()

    }
}