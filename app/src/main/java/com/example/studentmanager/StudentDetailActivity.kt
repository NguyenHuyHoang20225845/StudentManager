package com.example.studentmanager

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class StudentDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_detail)

        val edtMSSV = findViewById<EditText>(R.id.edtMSSV)
        val edtName = findViewById<EditText>(R.id.edtName)
        val edtPhone = findViewById<EditText>(R.id.edtPhone)
        val edtAddress = findViewById<EditText>(R.id.edtAddress)
        val btnUpdate = findViewById<Button>(R.id.btnUpdate)

        val student = intent.getParcelableExtra<Student>("student")

        if (student != null) {
            edtMSSV.setText(student.mssv)
            edtName.setText(student.name)
            edtPhone.setText(student.phone)
            edtAddress.setText(student.address)
        }

        btnUpdate.setOnClickListener {
            val mssv = edtMSSV.text.toString()
            val name = edtName.text.toString()
            val phone = edtPhone.text.toString()
            val address = edtAddress.text.toString()

            if (mssv.isEmpty() || name.isEmpty() || phone.isEmpty() || address.isEmpty()) return@setOnClickListener

            val updatedStudent = Student(mssv, name, phone, address)

            val resultIntent = Intent()
            resultIntent.putExtra("updated_student", updatedStudent)
            resultIntent.putExtra("original_student", student)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }
}
