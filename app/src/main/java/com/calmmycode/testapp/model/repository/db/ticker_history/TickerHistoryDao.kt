package com.calmmycode.testapp.model.repository.db.ticker_history

import androidx.paging.DataSource
import androidx.paging.PagedList
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.calmmycode.testapp.model.data.TickerDetailData
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

@Dao
abstract class TickerHistoryDao {
    @Query("SELECT * FROM TickerHistory WHERE pair_name =:pair_name")
    abstract fun getAllTickerHistory(pair_name : String) : DataSource.Factory<Int, TickerDetailData>

    @Query("DELETE FROM TickerHistory WHERE (:time - time) > 300000")
    abstract fun cleanTable(time : Long)

    @Insert
    abstract fun insertItem(tickerHistory: TickerHistory)

    @Transaction
    open fun insertAndClean(tickerHistory: TickerHistory){
        //cleanTable(tickerHistory.time)
        insertItem(tickerHistory)
    }

    fun insert(tickerHistory : TickerHistory) : Completable{
        return Completable.fromAction {
            insertAndClean(tickerHistory)
        }
    }
}