package com.example.todolist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.databinding.ItemTodoBinding
import com.example.todolist.model.Todo

class TodoAdapter(var Todos: List<Todo>, val onClickDeleteButton: (todo: Todo) -> Unit, val onCheckedChange: (todo: Todo, isCheck: Boolean) -> Unit): RecyclerView.Adapter<TodoAdapter.ToDoViewHolder>() {

    private lateinit var itemBinding: ItemTodoBinding

    inner class ToDoViewHolder(private val itemBinding: ItemTodoBinding): RecyclerView.ViewHolder(itemBinding.root){
        fun bind(data:Todo) {
            itemBinding.tvJob.text = data.job
            itemBinding.cbIsDone.setOnCheckedChangeListener(null)
            itemBinding.cbIsDone.isChecked = data.isDone

            itemBinding.btnDelete.setOnClickListener {
                onClickDeleteButton.invoke(data)
            }

            itemBinding.cbIsDone.setOnCheckedChangeListener { _, isChecked ->
                onCheckedChange.invoke(data, isChecked)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoViewHolder {
        itemBinding = ItemTodoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ToDoViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ToDoViewHolder, position: Int) {
        holder.bind(Todos[position])
    }

    override fun getItemCount(): Int {
        return Todos.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun setDoneData(doneData: List<Todo>){
        Todos = doneData
        notifyDataSetChanged()
    }

    fun setactiveData(activeData: List<Todo>){
        Todos = activeData
        notifyDataSetChanged()
    }

    fun setallData(allData: List<Todo>){
        Todos = allData
        notifyDataSetChanged()
    }

}