package com.example.todo_list.database

class TodoRepository (
    private val db:TodoDatabase
){
    suspend fun insert(todo: Todo) = db.getTodoDao().insert(todo)
    suspend fun delete(todo: Todo) = db.getTodoDao().delete(todo)

    fun getAllTdoItems():List<Todo> = db.getTodoDao().getAllTodoItems()
}