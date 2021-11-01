package com.example.roomwordsample

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class StudentRepository(private val studentDao: StudentDao) {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val allStudents: Flow<List<Student>> = studentDao.getAlphabetizedStudents()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(student: Student) {
        studentDao.insert(student)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(student: Student) {
        studentDao.delete(student)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(student: Student) {
        studentDao.update(student)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun updateStudent(id: Int, name: String) {
        studentDao.updateStudent(id, name)
    }
}