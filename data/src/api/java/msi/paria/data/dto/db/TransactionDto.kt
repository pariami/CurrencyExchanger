package msi.paria.data.dto.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import msi.paria.domain.model.Transaction

@Entity(tableName = "transaction")
class TransactionDto(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "amount")
    val amount: Double,
    @ColumnInfo(name = "fromCurrency")
    val fromCurrency: String,
    @ColumnInfo(name = "toCurrency")
    val toCurrency: String,
    @ColumnInfo(name = "convertedAmount")
    val convertedAmount: Double,
    @ColumnInfo(name = "commissionFee")
    val commissionFee: Double
)

fun TransactionDto.toTransaction():Transaction{
    return Transaction(id = id,
        amount = amount, fromCurrency = fromCurrency,
        toCurrency = toCurrency,
        convertedAmount = convertedAmount,
        commissionFee = commissionFee)
}

fun Transaction.toTransactionDto():TransactionDto{
    return TransactionDto(id = id,
        amount = amount, fromCurrency = fromCurrency,
        toCurrency = toCurrency,
        convertedAmount = convertedAmount,
        commissionFee = commissionFee)
}