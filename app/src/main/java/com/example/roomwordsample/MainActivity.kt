package com.example.roomwordsample

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), StudentListAdapter.ListAction {

    private val createStudentActivityRequestCode = 1
    private val editStudentActivityRequestCode = 2
    private val studentViewModel: StudentViewModel by viewModels {
        StudentViewModelFactory((application as StudentsApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = StudentListAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Add an observer on the LiveData returned by getAlphabetizedWords.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        studentViewModel.allStudents.observe(this) { students ->
            // Update the cached copy of the students in the adapter.
            students.let { adapter.submitList(it) }
        }

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this@MainActivity, CreateStudentActivity::class.java)
            startActivityForResult(intent, createStudentActivityRequestCode)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)

        if (requestCode == createStudentActivityRequestCode && resultCode == Activity.RESULT_OK) {
            intentData?.getStringExtra(CreateStudentActivity.EXTRA_REPLY)?.let { reply ->
                val student = Student(0 , reply)
                studentViewModel.insert(student)
            }
        } else if (requestCode == editStudentActivityRequestCode && resultCode == Activity.RESULT_OK) {
            val id = intentData?.getStringExtra(EditStudentActivity.ID)!!.toInt()
            val name = intentData?.getStringExtra(EditStudentActivity.NAME).toString()
            studentViewModel.updateStudent(id, name)
        } else {
            Toast.makeText(
                applicationContext,
                R.string.empty_not_saved,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onDelete(student: Student) {
        studentViewModel.delete(student)
    }

    override fun onEdit(student: Student) {
        val intent = Intent(this@MainActivity, EditStudentActivity::class.java)
        intent.putExtra("id",student.id);
        intent.putExtra("name",student.name);
        startActivityForResult(intent, editStudentActivityRequestCode)
    }
}