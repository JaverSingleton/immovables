package ru.vstu.immovables.di

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.vstu.immovables.App
import ru.vstu.immovables.PropertiesProvider
import ru.vstu.immovables.api.di.ApiModule
import ru.vstu.immovables.database.MainDatabase
import ru.vstu.immovables.database.di.DatabaseModule
import ru.vstu.immovables.repository.account.AccountRepository
import ru.vstu.immovables.repository.account.AccountRepositoryImpl
import ru.vstu.immovables.repository.estimate.EstimateRepository
import ru.vstu.immovables.repository.estimate.EstimateRepositoryImpl
import ru.vstu.immovables.repository.report.ReportRepository
import ru.vstu.immovables.repository.report.ReportRepositoryImpl
import javax.inject.Singleton

@Module(includes = arrayOf(ApiModule::class, DatabaseModule::class))
class AppModule {

    @Provides
    @Singleton
    fun provideApplicationContext(app: App): Context = app


    @Provides
    @Singleton
    fun provideProperty(context: Context): PropertiesProvider = PropertiesProvider(context)

    @Provides
    @Singleton
    fun provideReportRepository(database: MainDatabase, accountRepository: AccountRepository): ReportRepository =
            ReportRepositoryImpl(database.getReportDao(), accountRepository)

    @Provides
    @Singleton
    fun provideEstimateRepository(reportRepository: ReportRepository): EstimateRepository =
            EstimateRepositoryImpl(reportRepository)

    @Provides
    @Singleton
    fun provideAccountRepository(database: MainDatabase): AccountRepository =
            AccountRepositoryImpl(database.getAccountDao(), database.getCurrentAccountDao())

}