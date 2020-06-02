package com.calmmycode.testapp.di.modules

import com.calmmycode.testapp.di.scopes.ActivityScoped
import com.calmmycode.testapp.ui.main_activity.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(
        modules = [
            MainActivityModule::class,
            TickersFragmentModule::class,
            TickerDetailsFragmentModule::class
        ]
    )
    internal abstract fun mainActivity(): MainActivity
}