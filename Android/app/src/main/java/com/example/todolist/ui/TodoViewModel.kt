package com.example.todolist.ui

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todolist.model.Todo

class TodoViewModel : ViewModel() {

    val todoList = MediatorLiveData<List<Todo>>()
    private var datas = arrayListOf<Todo>()
    val allList = MediatorLiveData<List<Todo>>()
    val activeList = MutableLiveData<List<Todo>>()
    val doneList = MutableLiveData<List<Todo>>()

    init{
        todoList.addSource(allList){
                value->todoList.value = value
        }
        todoList.addSource(activeList){
                value->todoList.value = value
        }
        todoList.addSource(doneList){
                value -> todoList.value = value
        }
    }

    fun addList(todo: Todo){
        datas.add(todo)
        setData(datas)
    }
    fun deleteList(todo: Todo){
        datas.remove(todo)
        setData(datas)
    }

    fun updateToggle(todo:Todo, isCheck: Boolean) {
        if (todo.isDone != isCheck) {
            todo.isDone = isCheck
        }
        setData(datas)
    }

    private fun setData(data: ArrayList<Todo>){

        activeList.value = data.filter { x-> !x.isDone }.toList()
        doneList.value = data.filter { x->x.isDone }.toList()
        allList.value = data
        todoList.value = data
    }
}