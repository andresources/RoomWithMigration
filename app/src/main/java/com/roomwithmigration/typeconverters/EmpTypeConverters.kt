package com.roomwithmigration.typeconverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class EmpTypeConverters {
    val gson = Gson()
    @TypeConverter
    fun empToString(emp: Emp): String {
        return gson.toJson(emp)
    }

    @TypeConverter
    fun stringToEmp(empString: String): Emp {
        val objectType = object : TypeToken<Emp>() {}.type
        return gson.fromJson(empString, objectType)
    }
}