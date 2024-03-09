package msi.paria.currencyexchanger.main.view.pages.currency

sealed class CurrencyEvent {
    data class Success(val result: String): CurrencyEvent()
    data class Failure(val error: String): CurrencyEvent()
    data object Loading: CurrencyEvent()
    data object Empty: CurrencyEvent()
}