package ru.vstu.immovables.ui.login.di

import dagger.Subcomponent
import ru.vstu.immovables.di.PerActivity
import ru.vstu.immovables.ui.login.LoginActivity

@PerActivity
@Subcomponent(modules = arrayOf(LoginModule::class))
interface LoginComponent {

    fun inject(activity: LoginActivity)

}