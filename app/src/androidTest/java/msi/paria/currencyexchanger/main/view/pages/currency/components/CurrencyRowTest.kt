package msi.paria.currencyexchanger.main.view.pages.currency.components

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onChild
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.text.input.TextFieldValue
import msi.paria.currencyexchanger.R
import msi.paria.currencyexchanger.main.view.pages.currency.component.CurrencyRow
import msi.paria.currencyexchanger.util.TestTags
import org.junit.Rule
import org.junit.Test

class CurrencyRowTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun givenCurrencyRow_whenRendered_thenDisplaysTextAmountAndDropdown() {
        val text = "Sell"
        val iconRes = R.drawable.ic_sell
        val iconBack = Color.Red
        val amount = "100.00"
        val rates = listOf("USD", "EUR")
        val selectedCurrencyIndex = 0

        composeTestRule.setContent {
            CurrencyRow(
                text = text,
                iconRes = iconRes,
                iconBack = iconBack,
                amount = TextFieldValue(amount),
                rates = rates,
                selectedCurrencyIndex = selectedCurrencyIndex,
                onAmountValueEntered = {},
                onCurrencySelected = {}
            )
        }

        composeTestRule.onNodeWithText(text, useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithContentDescription(text, useUnmergedTree = true)
            .assertExists()

        composeTestRule.onNodeWithContentDescription(text, useUnmergedTree = true)
            .onChild()

        composeTestRule.onNodeWithText(amount, useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithTag(TestTags.dropDownTag, useUnmergedTree = true)
            .assertExists()

        composeTestRule.onNodeWithText(amount, useUnmergedTree = true)
            .assertIsDisplayed()
            .assertIsEnabled()

        composeTestRule.onNodeWithTag(TestTags.dropDownTag, useUnmergedTree = true)
            .assertExists()
    }
}