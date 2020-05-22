package com.calmmycode.testapp.model.data

data class TickerDetailData (
    val id : Long,
    val pair_name : String,
    val bid : Double,
    val ask : Double,
    val time : Long,
    val volume : Double,
    val last_price : Double
)