package com.calmmycode.testapp.model.repository.db.ticker_history

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TickerHistory (
    @PrimaryKey(autoGenerate = true)
    val id : Long?,
    val pair_name : String,
    val bid : Double,
    val ask : Double,
    val time : Long,
    val volume : Double,
    val last_price : Double
)