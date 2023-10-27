package com.roomwithmigration

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Dao
interface StudentDao{
    @Query("select * from tb_student")
    fun getAllStudents() : LiveData<List<StudentEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addStudent(std: StudentEntity)
}

@Database(entities = [StudentEntity::class,TeacherEntity::class,SchoolEntity::class], version = 4)
abstract class StudentDatabase : RoomDatabase(){
    abstract val studentDao: StudentDao
}
//MANUAL MIGRATION
val MIGRATE_1_2 = object : Migration(1,2){
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE tb_student ADD COLUMN cname TEXT NOT NULL DEFAULT '-'")
    }
}

val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("CREATE TABLE `Teacher` (`id` INTEGER NOT NULL, `name` TEXT NOT NULL, " +
                "PRIMARY KEY(`id`))")
    }
}
val MIGRATION_3_4 = object : Migration(3, 4) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("CREATE TABLE `tb_school` (`id` INTEGER NOT NULL, `name` TEXT NOT NULL, " +
                "PRIMARY KEY(`id`))")
    }
}

@Volatile
lateinit var INSTANCE: StudentDatabase
fun getStudentDB(context: Context) : StudentDatabase{
    if(!::INSTANCE.isInitialized){
        synchronized(StudentDatabase::class.java){
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                StudentDatabase::class.java,
                "mydb"
            ).allowMainThreadQueries().addMigrations(MIGRATE_1_2,MIGRATION_2_3,MIGRATION_3_4).build()
        }
    }
    return INSTANCE
}