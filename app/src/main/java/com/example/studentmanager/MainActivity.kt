package com.example.studentmanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.AdapterView
import android.widget.ListView

class MainActivity : AppCompatActivity() {

    private lateinit var lvStudent: ListView
    companion object {
        val studentList = ArrayList<Student>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lvStudent = findViewById(R.id.lvStudent)

        // Tạo danh sách mẫu (có thể xóa)
        if (studentList.isEmpty()) {
            studentList.add(Student("SV001", "Nguyễn Văn A", "0123456789", "Hà Nội"))
            studentList.add(Student("SV002", "Trần Thị B", "0987654321", "TP.HCM"))
        }

        val adapter = StudentAdapter(this, studentList)
        lvStudent.adapter = adapter

        // Xử lý click vào item
        lvStudent.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val intent = Intent(this, StudentDetailActivity::class.java)
            intent.putExtra("STUDENT_INDEX", position)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        // Refresh list khi quay lại từ activity khác
        (lvStudent.adapter as StudentAdapter).notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_add -> {
                val intent = Intent(this, AddStudentActivity::class.java)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}