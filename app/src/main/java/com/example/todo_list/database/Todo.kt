package com.example.todo_list.database

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Todo(
    var item:String?
){
    @PrimaryKey(autoGenerate = true)
    var id:Int?=null
}
