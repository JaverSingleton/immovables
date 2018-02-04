package ru.vstu.immovables.database.dao

import android.arch.persistence.room.*
import ru.vstu.immovables.database.entities.CurrentAccount

@Dao
interface CurrentAccountDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(report: CurrentAccount)

    @Delete
    fun delete(account: CurrentAccount)

    @Query("SELECT * FROM current_account WHERE id = 1")
    fun getCurrentAccount(): CurrentAccount?

}