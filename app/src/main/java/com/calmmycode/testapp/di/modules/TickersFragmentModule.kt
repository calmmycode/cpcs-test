package com.calmmycode.testapp.di.modules

import androidx.lifecycle.ViewModel
import com.calmmycode.testapp.di.scopes.FragmentScoped
import com.calmmycode.testapp.ui.main_activity.tickers_list.TickerListFragment
import com.calmmycode.testapp.ui.main_activity.tickers_list.TickerListViewModel
import com.google.samples.apps.iosched.shared.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class TickersFragmentModule {
    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeTickersListFragment(): TickerListFragment

    @Binds
    @IntoMap
    @ViewModelKey(TickerListViewModel::class)
    abstract fun bindTickerListViewModel(viewModel: TickerListViewModel): ViewModel
}