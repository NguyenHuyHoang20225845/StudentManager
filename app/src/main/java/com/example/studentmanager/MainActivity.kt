package com.example.studentmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var edtName: EditText
    private lateinit var edtMSSV: EditText
    private lateinit var btnAdd: Button
    private lateinit var btnUpdate: Button
    private lateinit var lvStudent: ListView

    private val list = ArrayList<Student>()
    private lateinit var adapter: StudentAdapter

    private var selectedIndex = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edtName = findViewById(R.id.edtName)
        edtMSSV = findViewById(R.id.edtMSSV)
        btnAdd = findViewById(R.id.btnAdd)
        btnUpdate = findViewById(R.id.btnUpdate)
        lvStudent = findViewById(R.id.lvStudent)

        adapter = StudentAdapter(this, list) { position ->
            list.removeAt(position)
            adapter.notifyDataSetChanged()
        }

        lvStudent.adapter = adapter

        // Add student
        btnAdd.setOnClickListener {
            val name = edtName.text.toString()
            val mssv = edtMSSV.text.toString()

            if (name.isEmpty() || mssv.isEmpty()) return@setOnClickListener

            list.add(Student(name, mssv))
            adapter.notifyDataSetChanged()

            edtName.setText("")
            edtMSSV.setText("")
        }

        // Select item to update
        lvStudent.setOnItemClickListener { _, _, position, _ ->
            selectedIndex = position
            val s = list[position]
            edtName.setText(s.name)
            edtMSSV.setText(s.mssv)
        }

        // Update student
        btnUpdate.setOnClickListener {
            if (selectedIndex == -1) return@setOnClickListener

            list[selectedIndex].name = edtName.text.toString()
            list[selectedIndex].mssv = edtMSSV.text.toString()

            adapter.notifyDataSetChanged()
            selectedIndex = -1

            edtName.setText("")
            edtMSSV.setText("")
        }
    }
}
