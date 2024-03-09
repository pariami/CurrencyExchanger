package msi.paria.currencyexchanger.main.view.pages.contract

sealed class CurrencyScreenEffect {
    data object OnRatesReceived: CurrencyScreenEffect()
    data object ShowResultDialog: CurrencyScreenEffect()
}