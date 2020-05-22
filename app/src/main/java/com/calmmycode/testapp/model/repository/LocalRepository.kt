package com.calmmycode.testapp.model.repository

import androidx.paging.PagedList
import com.calmmycode.testapp.model.data.TickerDetailData
import com.calmmycode.testapp.model.data.TickerItemData
import com.calmmycode.testapp.model.repository.db.ticker_history.TickerHistory
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

abstract class LocalRepository {
    abstract fun insertItemHistory(tickerHistory: TickerHistory) : Completable
    abstract fun getItemHistory(pair_name : String) : Observable<PagedList<TickerDetailData>>
    abstract fun getAllPairsWithTickets() : Observable<List<TickerItemData>>
}