package ru.vstu.immovables.di

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.vstu.immovables.App
import ru.vstu.immovables.PropertiesProvider
import ru.vstu.immovables.api.di.ApiModule
import ru.vstu.immovables.database.MainDatabase
import ru.vstu.immovables.database.di.DatabaseModule
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
    fun provideReportRepository(database: MainDatabase): ReportRepository =
            ReportRepositoryImpl(database.getReportDao())

    @Provides
    @Singleton
    fun provideEstimateRepository(reportRepository: ReportRepository): EstimateRepository =
            EstimateRepositoryImpl(reportRepository)

}