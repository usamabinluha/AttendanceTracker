package com.example.roomwordsample

import androidx.lifecycle.*
import kotlinx.coroutines.launch

class StudentViewModel(private val repository: StudentRepository) : ViewModel() {

    // Using LiveData and caching what allStudents returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allStudents: LiveData<List<Student>> = repository.allStudents.asLiveData()

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(student: Student) = viewModelScope.launch {
        repository.insert(student)
    }

    fun delete(student: Student) = viewModelScope.launch {
        repository.delete(student)
    }

    fun update(student: Student) = viewModelScope.launch {
        repository.update(student)
    }

    fun updateStudent(id: Int, name: String) = viewModelScope.launch {
        repository.updateStudent(id, name)
    }
}

class StudentViewModelFactory(private val repository: StudentRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StudentViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return StudentViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}