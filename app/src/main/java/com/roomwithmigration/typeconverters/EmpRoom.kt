package com.roomwithmigration.typeconverters

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import com.roomwithmigration.*

@Dao
interface EmpDao{
    @Query("select * from tb_emp")
    fun getAllEmps() : LiveData<List<EmpEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addEmp(emp: EmpEntity)
}
@Database(entities = [EmpEntity::class], version = 1)
@TypeConverters(EmpTypeConverters::class)
abstract class EmpDatabase : RoomDatabase(){
    abstract val empDao: EmpDao
}

@Volatile
lateinit var INSTANCE: EmpDatabase
fun getEmpDB(context: Context) : EmpDatabase {
    if(!::INSTANCE.isInitialized){
        synchronized(EmpDatabase::class.java){
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                EmpDatabase::class.java,
                "mydbemp"
            ).allowMainThreadQueries().build()
        }
    }
    return INSTANCE
}