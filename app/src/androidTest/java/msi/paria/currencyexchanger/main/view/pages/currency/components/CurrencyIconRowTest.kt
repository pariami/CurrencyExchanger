package msi.paria.currencyexchanger.main.view.pages.currency.components

import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import msi.paria.currencyexchanger.R
import msi.paria.currencyexchanger.main.view.pages.currency.component.CurrencyIconRow
import org.junit.Rule
import org.junit.Test

class CurrencyIconRowTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun givenCurrencyIconRow_whenRendered_thenDisplaysIconAndTextWithAdditionalContent() {
        val text = "Sell"
        val iconRes = R.drawable.ic_sell
        val iconBack = Color.Red

        composeTestRule.setContent {
            CurrencyIconRow(
                text = text,
                iconRes = iconRes,
                iconBack = iconBack
            ) {
                Text("Additional Content")
            }
        }

        composeTestRule.onNodeWithContentDescription(text, useUnmergedTree = true)
            .assertExists()

        composeTestRule.onNodeWithText(text, useUnmergedTree = true).assertExists()

        composeTestRule.onNodeWithText("Additional Content", useUnmergedTree = true).assertExists()
    }
}