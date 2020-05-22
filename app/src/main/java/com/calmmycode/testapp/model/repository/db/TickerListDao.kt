package com.calmmycode.testapp.model.repository.db

import androidx.room.Dao
import androidx.room.Query
import com.calmmycode.testapp.model.data.TickerItemData
import io.reactivex.Observable

@Dao
abstract class TickerListDao {
    @Query ("SELECT pair_name, round(bid, 3) bid_bid, round(ask, 3) ask, round(avg(last_price), 3) as avg FROM TickerHistory GROUP BY pair_name ORDER BY volume*last_price DESC")
    abstract fun getTickers() : Observable<List<TickerItemData>>
}