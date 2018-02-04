package ru.vstu.immovables.repository.account

import io.reactivex.Completable
import ru.vstu.immovables.database.dao.AccountDao
import ru.vstu.immovables.database.dao.CurrentAccountDao
import ru.vstu.immovables.database.entities.Account
import ru.vstu.immovables.database.entities.CurrentAccount
import java.util.concurrent.TimeUnit

interface AccountRepository {

    fun login(login: String, password: String): Completable

    fun logout(): Completable

    fun getLogin(): String

    fun isLoggedIn(): Boolean

}

class AccountRepositoryImpl(
        private val accountDao: AccountDao,
        private val currentAccountDao: CurrentAccountDao
) : AccountRepository {

    override fun login(login: String, password: String): Completable =
            Completable.timer(1, TimeUnit.SECONDS)
                    .andThen(Completable.fromAction {
                        val account = accountDao.getAccount(login.toLowerCase())
                        if (account == null) {
                            accountDao.insert(Account(login = login.toLowerCase(), password = password))
                        } else if (account.password != password) {
                            throw UnauthorizedException()
                        }
                        currentAccountDao.insert(CurrentAccount(login = login.toLowerCase()))
                    })

    override fun logout(): Completable =
            Completable.timer(1, TimeUnit.SECONDS)
                    .andThen(Completable.fromAction {
                        currentAccountDao.getCurrentAccount()?.let { currentAccountDao.delete(it) }
                    })

    override fun getLogin(): String =
            currentAccountDao.getCurrentAccount()?.login ?: throw UnauthorizedException()

    override fun isLoggedIn(): Boolean = currentAccountDao.getCurrentAccount() != null

}