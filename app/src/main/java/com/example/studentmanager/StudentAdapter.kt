package com.example.studentmanager

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import java.util.*

class StudentAdapter(
    context: Context,
    private val list: ArrayList<Student>,
    private val deleteListener: (Int) -> Unit
) : ArrayAdapter<Student>(context, 0, list) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.student_item, parent, false)

        val student = list[position]

        val txtName = view.findViewById<TextView>(R.id.txtName)
        val txtMSSV = view.findViewById<TextView>(R.id.txtMSSV)
        val btnDelete = view.findViewById<Button>(R.id.btnDelete)

        txtName.text = student.name
        txtMSSV.text = student.mssv

        btnDelete.setOnClickListener { deleteListener(position) }

        return view
    }
}
