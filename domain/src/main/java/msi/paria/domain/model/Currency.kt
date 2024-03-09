package msi.paria.domain.model


data class Currency(
    val base: String,
    val date: String,
    val rates: Map<String, Double>
    //val rates: Rates

)
