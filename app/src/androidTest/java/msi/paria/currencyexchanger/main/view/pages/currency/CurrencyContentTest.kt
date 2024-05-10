package msi.paria.currencyexchanger.main.view.pages.currency

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import msi.paria.domain.model.Balance
import org.junit.Rule
import org.junit.Test

class CurrencyContentTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun givenCurrencyContent_whenRendered_thenDisplaysBalancesCurrencyRowsSubmitButtonAndPopUpDialog() {
        val balances = listOf(
            Balance(1, "USD", 100.0),
            Balance(2, "EUR", 200.0)
        )
        val rates = listOf("USD", "EUR")

        composeTestRule.setContent {
            CurrencyContent(
                onAmountValueEntered = {},
                onFromCurrencySelected = {},
                onToCurrencySelected = {},
                onSubmitButtonClicked = {},
                rates = rates,
                message = "Test Message",
                balances = balances,
                convertedAmount = "50.0",
                showDialog = mutableStateOf(true),
                submitButtonEnabled = true,
                isLoading = false
            )
        }

        composeTestRule.onNodeWithText("My Balances", useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithText("USD: 100.0", useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithText("EUR: 200.0", useUnmergedTree = true).assertExists()

        composeTestRule.onNodeWithText("Sell", useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithText("USD", useUnmergedTree = true).assertExists()

        composeTestRule.onNodeWithText("Receive", useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithText("EUR", useUnmergedTree = true).assertExists()

        composeTestRule.onNodeWithText("Submit", useUnmergedTree = true).assertExists()
            .assertIsEnabled()

        composeTestRule.onNodeWithText("Test Message", useUnmergedTree = true).assertExists()
    }
}