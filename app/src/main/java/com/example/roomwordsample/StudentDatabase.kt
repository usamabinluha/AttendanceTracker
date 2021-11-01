package com.example.roomwordsample

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

// Annotates class to be a Room Database with a table (entity) of the Student class
@Database(entities = arrayOf(Student::class), version = 1, exportSchema = false)
public abstract class StudentDatabase : RoomDatabase() {

    abstract fun studentDao(): StudentDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: StudentDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): StudentDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    StudentDatabase::class.java,
                    "student_database"
                ).addCallback(StudentDatabaseCallback(scope)).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }

    private class StudentDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.studentDao())
                }
            }
        }

        suspend fun populateDatabase(studentDao: StudentDao) {
            // Delete all content here.
            studentDao.deleteAll()

            // Add sample words.
            var student = Student(1, "Jahir")
            studentDao.insert(student)
            student = Student(2, "Ibraaheem S")
            studentDao.insert(student)
        }
    }
}