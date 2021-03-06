package ru.vstu.immovables.database.dao

import android.arch.persistence.room.*
import ru.vstu.immovables.database.entities.Account

@Dao
interface AccountDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(report: Account)

    @Delete
    fun delete(account: Account)

    @Query("SELECT * FROM accounts WHERE login = :arg0")
    fun getAccount(login: String): Account?

}