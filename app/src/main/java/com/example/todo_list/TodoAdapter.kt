package com.example.todo_list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.todo_list.database.Todo
import com.example.todo_list.database.TodoRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TodoAdapter(items:List<Todo>,repository: TodoRepository,viewModel:MainActivityData): RecyclerView.Adapter<ToDoViewHolder>(){

    var items = items
    val repository = repository
    val viewModel = viewModel
    var context:Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_item,parent,false)
        context = parent.context

        return ToDoViewHolder(view)

    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ToDoViewHolder, position: Int) {
        holder.cbTodo.text = items.get(position).item
        holder.ivDelete.setOnClickListener{
            val isChecked = holder.cbTodo.isChecked
            if(isChecked){
                CoroutineScope(Dispatchers.IO).launch {
                    repository.delete(items.get(position))
                    val data = repository.getAllTdoItems()
                    withContext(Dispatchers.Main){
                        viewModel.setData(data)
                    }
                }
            }
        }
    }

}