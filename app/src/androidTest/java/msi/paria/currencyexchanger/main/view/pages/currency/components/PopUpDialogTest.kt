package msi.paria.currencyexchanger.main.view.pages.currency.components

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import msi.paria.currencyexchanger.main.view.pages.currency.component.PopUpDialog
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class PopUpDialogTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun givenPopUpDialog_whenRendered_thenDisplaysMessageAndAllowsConfirmation() {
        val message = "Test message"
        var dialogDismissed = false
        var dialogConfirmed = false
        val showDialog = true

        composeTestRule.setContent {
            PopUpDialog(
                message = message,
                onDismissDialog = { dialogDismissed = true },
                onConfirmDialog = { dialogConfirmed = true },
                showDialog = showDialog
            )
        }

        composeTestRule.onNodeWithText("Currency Converted").assertExists()
        composeTestRule.onNodeWithText(message).assertExists()
        composeTestRule.onNodeWithText("Done").assertExists()

        composeTestRule.onNodeWithText("Done").performClick()

        assertTrue(dialogConfirmed)
    }
}