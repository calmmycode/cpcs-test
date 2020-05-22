package com.calmmycode.testapp.di.modules

import com.calmmycode.testapp.di.scopes.FragmentScoped
import com.calmmycode.testapp.ui.main_activity.ticker_details.TickerDetailsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class TickerDetailsFragmentModule {
    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeTicketDetailsFragment(): TickerDetailsFragment
}