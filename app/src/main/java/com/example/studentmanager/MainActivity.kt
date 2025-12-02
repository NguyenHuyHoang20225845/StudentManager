package com.example.studentmanager

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import androidx.activity.result.contract.ActivityResultContracts
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var lvStudent: ListView

    private val list = ArrayList<Student>()
    private lateinit var adapter: StudentAdapter

    private val addStudentLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            val newStudent = it.data?.getParcelableExtra<Student>("new_student")
            if (newStudent != null) {
                list.add(newStudent)
                adapter.notifyDataSetChanged()
            }
        }
    }

    private val updateStudentLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            val updatedStudent = it.data?.getParcelableExtra<Student>("updated_student")
            val originalStudent = it.data?.getParcelableExtra<Student>("original_student")
            if (updatedStudent != null && originalStudent != null) {
                val index = list.indexOf(originalStudent)
                if (index != -1) {
                    list[index] = updatedStudent
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lvStudent = findViewById(R.id.lvStudent)

        // Dummy data
        list.add(Student("20505312001", "Nguyễn Văn A", "0123456789", "TP.HCM"))
        list.add(Student("20505312002", "Trần Thị B", "0987654321", "Hà Nội"))

        adapter = StudentAdapter(this, list) { position ->
            // Handle delete student
            list.removeAt(position)
            adapter.notifyDataSetChanged()
        }

        lvStudent.adapter = adapter

        lvStudent.setOnItemClickListener { _, _, position, _ ->
            val intent = Intent(this, StudentDetailActivity::class.java)
            intent.putExtra("student", list[position])
            updateStudentLauncher.launch(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add_student -> {
                val intent = Intent(this, AddStudentActivity::class.java)
                addStudentLauncher.launch(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
