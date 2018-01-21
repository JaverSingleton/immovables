package ru.vstu.immovables.database.di

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.vstu.immovables.database.MainDatabase
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(context: Context): MainDatabase =
            MainDatabase.createPersistenceDatabase(context)

}