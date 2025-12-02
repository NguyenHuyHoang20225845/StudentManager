package com.example.studentmanager

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class StudentDetailActivity : AppCompatActivity() {

    private lateinit var edtMSSV: EditText
    private lateinit var edtName: EditText
    private lateinit var edtPhone: EditText
    private lateinit var edtAddress: EditText
    private lateinit var btnUpdate: Button

    private var studentIndex = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_detail)

        edtMSSV = findViewById(R.id.edtMSSV)
        edtName = findViewById(R.id.edtName)
        edtPhone = findViewById(R.id.edtPhone)
        edtAddress = findViewById(R.id.edtAddress)
        btnUpdate = findViewById(R.id.btnUpdate)

        studentIndex = intent.getIntExtra("STUDENT_INDEX", -1)

        if (studentIndex != -1) {
            val student = MainActivity.studentList[studentIndex]
            edtMSSV.setText(student.mssv)
            edtName.setText(student.name)
            edtPhone.setText(student.phone)
            edtAddress.setText(student.address)
        }

        btnUpdate.setOnClickListener {
            val mssv = edtMSSV.text.toString().trim()
            val name = edtName.text.toString().trim()
            val phone = edtPhone.text.toString().trim()
            val address = edtAddress.text.toString().trim()

            if (mssv.isEmpty() || name.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập MSSV và Họ tên", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Kiểm tra trùng MSSV với sinh viên khác
            val existingIndex = MainActivity.studentList.indexOfFirst { it.mssv == mssv }
            if (existingIndex != -1 && existingIndex != studentIndex) {
                Toast.makeText(this, "MSSV đã tồn tại", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val student = MainActivity.studentList[studentIndex]
            student.mssv = mssv
            student.name = name
            student.phone = phone
            student.address = address

            Toast.makeText(this, "Đã cập nhật thông tin", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}