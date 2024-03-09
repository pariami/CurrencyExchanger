package msi.paria.currencyexchanger.main.view.pages.currency.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun PopUpDialog(
    message: String, onDismissDialog: () -> Unit, onConfirmDialog: () -> Unit, showDialog: Boolean
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { onDismissDialog() },
            title = { Text("Currency Converted") },
            text = { Text(message) },
            confirmButton = {
                Button(onClick = { onConfirmDialog() }) {
                    Text("Done")
                }
            },
        )
    }
}