package com.roomwithmigration.typeconverters

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tb_emp")
class EmpEntity (
        @PrimaryKey(autoGenerate = true)
        val id: Int,
        val emp: Emp
    )