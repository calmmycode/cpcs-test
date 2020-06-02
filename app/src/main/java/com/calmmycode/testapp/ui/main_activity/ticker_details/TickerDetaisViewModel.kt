package com.calmmycode.testapp.ui.main_activity.ticker_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import com.calmmycode.testapp.model.data.TickerDetailData
import com.calmmycode.testapp.model.repository.LocalRepository
import io.reactivex.Observable

class TickerDetaisViewModel(repository: LocalRepository, pair_name: String) : ViewModel() {

    // TODO: use assisted injection (google auto, etc.)

    val concertList: Observable<PagedList<TickerDetailData>> = repository.getItemHistory(pair_name)


    class Factory(
        val repository: LocalRepository,
        val pair_name: String
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return TickerDetaisViewModel(
                repository,
                pair_name
            ) as T
        }
    }
}