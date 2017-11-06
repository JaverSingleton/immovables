package ru.vstu.immovables.api.di

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.vstu.immovables.api.Api
import javax.inject.Singleton

@Module
class ApiModule {

    @Provides
    @Singleton
    fun provideApi(client: OkHttpClient,
                   converterFactory: Converter.Factory,
                   callAdapter: CallAdapter.Factory): Api =
            Retrofit.Builder()
                    .baseUrl(API_URL)
                    .client(client)
                    .addConverterFactory(converterFactory)
                    .addCallAdapterFactory(callAdapter)
                    .build()
                    .create(Api::class.java)

    @Provides
    @Singleton
    fun provideConverterFactory(gson: Gson): Converter.Factory =
            GsonConverterFactory.create(gson)

    @Provides
    @Singleton
    fun provideCallAdapter(): CallAdapter.Factory =
            RxJavaCallAdapterFactory.create()

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()

}

val API_URL = ""