package msi.paria.domain.model

data class Rate(
    val id: Int,
    val name: String,
    val date: String,
    val rates: Map<String, Double>
)