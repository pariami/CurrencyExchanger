package msi.paria.currencyexchanger.main.view.pages.contract

sealed class CurrencyScreenEvent {
    data object OnSubmitButtonClicked: CurrencyScreenEvent()
    data class OnFromCurrencySelected(var currency:String): CurrencyScreenEvent()
    data class OnAmountValueEntered(var amount:String): CurrencyScreenEvent()
    data class OnToCurrencySelected(var currency:String): CurrencyScreenEvent()
    data object OnCurrencyRateUpdated: CurrencyScreenEvent()
}