package msi.paria.domain.usecase

import msi.paria.domain.repository.ExchangeRepository

class GetExchangeRates(private val exchangeRepository: ExchangeRepository) {
    suspend operator fun invoke(base: String) = exchangeRepository.getExchangeRates(base)
}