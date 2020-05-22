package com.calmmycode.testapp.di.modules

import android.content.Context
import androidx.room.Room
import com.calmmycode.testapp.App
import com.calmmycode.testapp.constants.ConstantsAPI
import com.calmmycode.testapp.api.rest.API
import com.calmmycode.testapp.constants.ConstantsDB
import com.calmmycode.testapp.model.repository.LocalRepository
import com.calmmycode.testapp.model.repository.db.AppDatabase
import com.calmmycode.testapp.model.repository.db.LocalRepositoryImpl
import com.owlylabs.platform.di.java.qualifiers.BaseUrlRetrofit
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import javax.inject.Singleton

@Module
class AppModule {
    @Provides
    fun provideContext(application: App): Context {
        return application.applicationContext
    }

    @Provides
    @BaseUrlRetrofit
    fun provideBaseUrlRetrofit() : String {
        return ConstantsAPI.BASE_URL
    }

    @Provides
    @Singleton
    fun provideRetrofit(@BaseUrlRetrofit baseUrl: String): API {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            //.addConverterFactory(ScalarsConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(API::class.java)
    }

    @Provides
    @Singleton
    fun provideRoom(context: Context): AppDatabase {
        return Room
            .databaseBuilder(
                context,
                AppDatabase::class.java,
                ConstantsDB.databaseName
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideTabsRepository(room: AppDatabase) : LocalRepository {
        return LocalRepositoryImpl(room)
    }
}