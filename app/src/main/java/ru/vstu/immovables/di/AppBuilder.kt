package ru.vstu.immovables.di

import dagger.Module


@Module(includes = arrayOf(AppModule::class))
interface AppBuilder {

}