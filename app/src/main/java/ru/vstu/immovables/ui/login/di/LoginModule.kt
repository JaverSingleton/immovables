package ru.vstu.immovables.ui.login.di

import android.os.Bundle
import dagger.Module
import dagger.Provides
import ru.vstu.immovables.repository.account.AccountRepository
import ru.vstu.immovables.ui.login.LoginPresenter
import ru.vstu.immovables.ui.login.LoginPresenterImpl

@Module
class LoginModule(
        private val presenterState: Bundle?
) {

    @Provides
    fun providePresenter(accountRepository: AccountRepository): LoginPresenter =
            LoginPresenterImpl(accountRepository, presenterState)

}