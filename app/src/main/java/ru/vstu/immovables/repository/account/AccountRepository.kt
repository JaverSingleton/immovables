package ru.vstu.immovables.repository.account

import io.reactivex.Completable
import ru.vstu.immovables.database.dao.AccountDao
import ru.vstu.immovables.database.entities.Account
import java.util.concurrent.TimeUnit

interface AccountRepository {

    fun login(login: String, password: String): Completable

    fun logout(): Completable

    fun getLogin(): String

    fun isLoggedIn(): Boolean

}

class AccountRepositoryImpl(private val accountDao: AccountDao) : AccountRepository {

    override fun login(login: String, password: String): Completable =
            Completable.timer(1, TimeUnit.SECONDS)
                    .andThen(Completable.fromAction { accountDao.insert(Account(login = login)) })

    override fun logout(): Completable =
            Completable.timer(1, TimeUnit.SECONDS)
                    .andThen(Completable.fromAction { accountDao.getAccount()?.let { accountDao.delete(it) } })

    override fun getLogin(): String =
            accountDao.getAccount()?.login ?: throw UnauthorizedException()

    override fun isLoggedIn(): Boolean = accountDao.getAccount() != null

}