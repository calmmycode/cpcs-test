package com.calmmycode.testapp.di

import com.calmmycode.testapp.App
import com.calmmycode.testapp.di.modules.ActivityBindingModule
import com.calmmycode.testapp.di.modules.AppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component (modules = [
    AndroidSupportInjectionModule::class,
    AppModule::class,
    ActivityBindingModule::class]
)
interface AppComponent : AndroidInjector<App> {

    @Component.Factory
    interface Factory{
        fun create(@BindsInstance app: App) : AppComponent
    }

    //fun getRetrofit() : API
}