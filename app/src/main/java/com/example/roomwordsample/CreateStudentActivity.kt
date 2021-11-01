package com.example.roomwordsample

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText

class CreateStudentActivity : AppCompatActivity() {

    private lateinit var editStudentView: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_student)

        editStudentView = findViewById(R.id.edit_student_name)

        val button = findViewById<Button>(R.id.button_add)
        button.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(editStudentView.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val student = editStudentView.text.toString()
                replyIntent.putExtra(EXTRA_REPLY, student)
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
    }

    companion object {
        const val EXTRA_REPLY = "com.example.android.studentlistsql.REPLY"
    }
}