package com.safeboda.test.core

import androidx.room.Database
import androidx.room.RoomDatabase
import com.safeboda.test.user.data.local.dao.UserDao
import com.safeboda.test.user.data.model.User

@Database(
    entities = [User::class],
    version = 1,
    exportSchema = false
)
abstract class SafeBoadDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

}