package com.example.todo_list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.RecoverySystem
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todo_list.database.Todo
import com.example.todo_list.database.TodoDatabase
import com.example.todo_list.database.TodoRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var  adapter: TodoAdapter
    private lateinit var viewModel:MainActivityData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView:RecyclerView = findViewById(R.id.rvTodoList)
        val repository = TodoRepository(TodoDatabase.getInstance(this))
        viewModel = ViewModelProvider(this)[MainActivityData::class.java]

        viewModel.data.observe(this) {
            adapter = TodoAdapter(it,repository,viewModel)
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(this)
        }
        CoroutineScope(Dispatchers.IO).launch {
            val data = repository.getAllTdoItems()
            runOnUiThread {
                viewModel.setData(data)
            }
        }

        val addItem : Button = findViewById(R.id.btnAddTodo)

        addItem.setOnClickListener{
            displayAlert(repository)
        }
    }

    private fun displayAlert(repository:TodoRepository){
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getText(R.string.alertTitle))
        builder.setMessage("Enter todo item below:")

        //create edit text
        val input = EditText(this)
        input.inputType = InputType.TYPE_CLASS_TEXT
        builder.setView(input)

        //set positive button action
        builder.setPositiveButton("ok"){dialog,which->
            //get input text and display toast msg
            val item = input.text.toString()
            CoroutineScope(Dispatchers.IO).launch {
                repository.insert(Todo(item))
                val data = repository.getAllTdoItems()
                runOnUiThread {
                    viewModel.setData(data)
                }
            }
        }

        builder.setNegativeButton("cancel"){dialog,which ->
            dialog.cancel()
        }

        // Create and show the alert dialog
        val alertDialog = builder.create()
        alertDialog.show()
    }
}