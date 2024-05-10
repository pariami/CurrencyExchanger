package msi.paria.currencyexchanger.main.view.pages.currency.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsFocused
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import msi.paria.currencyexchanger.main.view.pages.currency.component.CurrencyTextField
import org.junit.Rule
import org.junit.Test

class CurrencyTextFieldTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun givenCurrencyTextField_whenRendered_thenDisplaysInitialValue() {
        val initialValue = "100.00"
        var enteredValue = ""

        composeTestRule.setContent {
            MaterialTheme {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    CurrencyTextField(
                        value = initialValue,
                        onAmountValueChanged = { enteredValue = it }
                    )
                }
            }
        }

        composeTestRule.onNodeWithText(initialValue).assertExists()

        composeTestRule.onNodeWithText(initialValue).performClick()

        composeTestRule.onNodeWithText(initialValue).assertIsFocused()
    }
}