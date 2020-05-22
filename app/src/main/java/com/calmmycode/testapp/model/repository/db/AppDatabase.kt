package com.calmmycode.testapp.model.repository.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.calmmycode.testapp.model.repository.db.ticker_history.TickerHistory
import com.calmmycode.testapp.model.repository.db.ticker_history.TickerHistoryDao

@Database(entities = [TickerHistory::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun history(): TickerHistoryDao
    abstract fun tickerList(): TickerListDao

    companion object {
        private const val databaseName = "db"

        fun buildDatabase(context: Context): AppDatabase {
            // Since Room is only used for FTS, destructive migration is enough because the tables
            // are cleared every time the app launches.
            // https://medium.com/androiddevelopers/understanding-migrations-with-room-f01e04b07929
            return Room.databaseBuilder(context, AppDatabase::class.java, databaseName)
                .build()
        }
    }
}