package msi.paria.currencyexchanger.main.view.pages.currency.components

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import msi.paria.currencyexchanger.main.view.pages.currency.component.SubmitButton
import org.junit.Rule
import org.junit.Test

class SubmitButtonTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun givenSubmitButton_whenRendered_thenDisplaysButtonAndAllowsClick() {
        val buttonText = "Submit"
        var buttonClicked = false

        composeTestRule.setContent {
            SubmitButton(
                text = buttonText,
                onSubmitButtonClicked = { buttonClicked = true },
                submitButtonEnabled = true
            )
        }

        composeTestRule.onNodeWithText(buttonText).assertExists()

        composeTestRule.onNodeWithText(buttonText).performClick()

        assert(buttonClicked)
    }
}