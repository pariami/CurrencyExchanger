package msi.paria.domain.usecase

import javax.inject.Inject

data class InternalUseCse @Inject constructor(
    val currencyConversion: CurrencyConversion,
    val transferFunds: TransferFunds,
    val getCommissionUseCase: GetCommission,
    val checkBalance: CheckBalance
)