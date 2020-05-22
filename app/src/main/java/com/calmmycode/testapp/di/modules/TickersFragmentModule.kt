package com.calmmycode.testapp.di.modules

import com.calmmycode.testapp.di.scopes.FragmentScoped
import com.calmmycode.testapp.ui.main_activity.tickers_list.TickerListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class TickersFragmentModule {
    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeTickersListFragment(): TickerListFragment
}