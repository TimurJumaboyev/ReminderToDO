package uz.isystem.remindertodo.core.di

import android.content.Context
import androidx.room.Room
import uz.isystem.remindertodo.core.db.DataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {


    @Provides
    @Singleton
    fun getDB(@ApplicationContext context: Context): DataBase =
        Room.databaseBuilder(context, DataBase::class.java, "database")
            .allowMainThreadQueries()
            .build()


}