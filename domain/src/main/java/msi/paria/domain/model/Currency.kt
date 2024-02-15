package msi.paria.domain.model

data class Currency(
    val code: String, // e.g. USD, EUR, etc.
    val name: String, // e.g. US Dollar, Euro, etc.
    val symbol: String // e.g. $, €, etc.
)