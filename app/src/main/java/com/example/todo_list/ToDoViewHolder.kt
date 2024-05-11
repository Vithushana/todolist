package com.example.todo_list

import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class ToDoViewHolder(view:View):ViewHolder(view) {
    val cbTodo:CheckBox
    val ivDelete:ImageView

    init {
        cbTodo = view.findViewById(R.id.cbTodo)
        ivDelete = view.findViewById(R.id.ivDelete)
    }
}

