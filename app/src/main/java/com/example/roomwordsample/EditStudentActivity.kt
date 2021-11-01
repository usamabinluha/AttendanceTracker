package com.example.roomwordsample

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText

class EditStudentActivity : AppCompatActivity() {

    private lateinit var editStudentView: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_student)

        val extras = intent.extras
        val id = extras?.get("id")
        val name = extras?.get("name").toString()

        editStudentView = findViewById(R.id.edit_student_name)
        editStudentView.setText(name)

        val button = findViewById<Button>(R.id.button_add)
        button.text = getString(R.string.button_save)

        button.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(editStudentView.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val name = editStudentView.text.toString()
                replyIntent.putExtra(ID, id.toString())
                replyIntent.putExtra(NAME, name)
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
    }

    companion object {
        const val ID = "com.example.android.studentlistsql.ID"
        const val NAME = "com.example.android.studentlistsql.NAME"
    }
}