package com.calmmycode.testapp.ui.main_activity.tickers_list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import com.calmmycode.testapp.model.data.TickerDetailData
import com.calmmycode.testapp.model.data.TickerItemData
import com.calmmycode.testapp.model.repository.LocalRepository
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class TickerListViewModel @Inject constructor(repository: LocalRepository) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    val newData : MutableLiveData<List<TickerItemData>> = MutableLiveData()

    init {
        compositeDisposable.add(repository.getAllPairsWithTickets()
            .subscribeBy(
                onError = {},
                onComplete = {},
                onNext = { newData.postValue(it) }
            ))
    }

    override fun onCleared() {
        compositeDisposable.dispose()
    }
}