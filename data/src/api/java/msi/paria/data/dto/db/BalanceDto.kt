package msi.paria.data.dto.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import msi.paria.domain.model.Balance
import msi.paria.domain.model.Transaction

@Entity(tableName = "balance")
class BalanceDto(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "amount") val amount: Double,
)

fun BalanceDto.toBalance(): Balance {
    return Balance(
        id = id, name = name, amount = amount
    )
}

fun Balance.toBalanceDto(): BalanceDto {
    return BalanceDto(
        id = id, name = name, amount = amount
    )
}