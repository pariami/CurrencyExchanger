package msi.paria.domain.usecase

import msi.paria.domain.repository.ExchangeRepository
import javax.inject.Inject

class GetExchangeRates @Inject constructor(private val exchangeRepository: ExchangeRepository) {
    suspend operator fun invoke(base: String) = exchangeRepository.getExchangeRates(base)
}