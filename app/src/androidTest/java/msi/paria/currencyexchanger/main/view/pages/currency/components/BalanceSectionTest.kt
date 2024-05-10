package msi.paria.currencyexchanger.main.view.pages.currency.components

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import msi.paria.currencyexchanger.main.view.pages.currency.component.BalanceSection
import msi.paria.domain.model.Balance
import org.junit.Rule
import org.junit.Test

class BalanceSectionTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun givenBalanceSection_whenRendered_thenDisplaysBalancesAndText() {
        val balances = listOf(
            Balance(id = 1, name = "USD", amount = 100.0),
            Balance(id = 2, name = "EUR", amount = 200.0)
        )
        val textColor = Color.Blue

        composeTestRule.setContent {
            BalanceSection(
                text = "My Balances",
                balancesItems = balances,
                color = textColor
            )
        }

        composeTestRule.onNodeWithText("My Balances", useUnmergedTree = true).assertExists()


        balances.forEach { balance ->
            composeTestRule.onNodeWithText(
                "${balance.amount} ${balance.name}",
                useUnmergedTree = true
            ).assertExists()
        }
    }
}