package com.roomwithmigration

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import java.util.UUID

class MainActivity : AppCompatActivity() {
    lateinit var tv: TextView
    lateinit var btn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tv = findViewById(R.id.tv)
        btn = findViewById(R.id.button)
        getStudentDB(this).studentDao.getAllStudents().observe(this,{
            tv.text = "Size : ${it.size}"
        })

        btn.setOnClickListener {
            getStudentDB(this).studentDao.addStudent(StudentEntity(UUID.randomUUID().toString(),2,"-"))
        }
    }
}