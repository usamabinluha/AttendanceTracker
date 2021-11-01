package com.example.roomwordsample

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface StudentDao {
    @Query("SELECT * FROM student_table ORDER BY name ASC")
    fun getAlphabetizedStudents(): Flow<List<Student>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(student: Student)

    @Update
    suspend fun update(student: Student)

    @Query("UPDATE student_table SET name = :name WHERE id = :id")
    suspend fun updateStudent(id: Int, name: String)

    @Delete
    suspend fun delete(student: Student)

    @Query("DELETE FROM student_table")
    suspend fun deleteAll()
}