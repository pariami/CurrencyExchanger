package msi.paria.currencyexchanger.main.view.pages.currency.component

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SubmitButton(text: String,
                 modifier: Modifier = Modifier,
                 onSubmitButtonClicked:()->Unit,
                 submitButtonEnabled:Boolean){
    Button(
        onClick = { onSubmitButtonClicked() },
        modifier = modifier,
        enabled = submitButtonEnabled
    ) {
        Text(text = text)
    }
}