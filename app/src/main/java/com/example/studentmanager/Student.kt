package com.example.studentmanager

data class Student(
    var mssv: String,
    var name: String,
    var phone: String = "",
    var address: String = ""
)