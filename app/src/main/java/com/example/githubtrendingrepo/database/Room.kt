package com.example.githubtrendingrepo.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*



@Dao
interface RepoDao {
    @Query("select * from databaserepo")
    fun getRepos(): LiveData<List<DatabaseRepo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll( repos: List<DatabaseRepo>)
}



@Database(entities = [DatabaseRepo::class], version = 1)
abstract class ReposDatabase: RoomDatabase() {
    abstract val repoDao: RepoDao
}

private lateinit var INSTANCE: ReposDatabase

fun getDatabase(context: Context): ReposDatabase {
    synchronized(ReposDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                    ReposDatabase::class.java,
                    "repos").build()
        }
    }
    return INSTANCE
}