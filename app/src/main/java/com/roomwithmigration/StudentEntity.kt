package com.roomwithmigration

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tb_student")
data class StudentEntity(
    @PrimaryKey
    val sname: String,
    val sno:Int,
    val cname: String
    )

@Entity(tableName = "Teacher")
data class TeacherEntity(
    @PrimaryKey
    val id: Int,
    val name: String
)

@Entity(tableName = "tb_school")
data class SchoolEntity(
    @PrimaryKey
    val id: Int,
    val name: String
)