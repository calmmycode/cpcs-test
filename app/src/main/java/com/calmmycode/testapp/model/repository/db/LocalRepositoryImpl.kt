package com.calmmycode.testapp.model.repository.db

import androidx.paging.PagedList
import androidx.paging.toObservable
import com.calmmycode.testapp.model.data.TickerDetailData
import com.calmmycode.testapp.model.data.TickerItemData
import com.calmmycode.testapp.model.repository.LocalRepository
import com.calmmycode.testapp.model.repository.db.ticker_history.TickerHistory
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class LocalRepositoryImpl(val appDatabase: AppDatabase) : LocalRepository() {
    override fun insertItemHistory(tickerHistory: TickerHistory): Completable {
        return appDatabase.history().insert(tickerHistory).subscribeOn(Schedulers.io())
    }

    override fun getItemHistory(pair_name : String): Observable<PagedList<TickerDetailData>> {
        return appDatabase.history().getAllTickerHistory(pair_name).toObservable(pageSize = 5)
            .subscribeOn(Schedulers.io())
    }

    override fun getAllPairsWithTickets(): Observable<List<TickerItemData>> {
        return appDatabase.tickerList().getTickers().subscribeOn(Schedulers.io())
    }
}