package com.example.todolist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist.databinding.FragmentDoneBinding
import com.example.todolist.model.Todo
import com.example.todolist.ui.TodoViewModel

class DoneFragment: Fragment() {

    private lateinit var viewModel: TodoViewModel
    private lateinit var binding: FragmentDoneBinding
    private lateinit var adapter : TodoAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(requireActivity(), ViewModelProvider.NewInstanceFactory()) .get(TodoViewModel::class.java)
        binding = FragmentDoneBinding.inflate(inflater, container, false)

        var doneList = viewModel.doneList.value
        adapter = TodoAdapter(
            doneList?: emptyList<Todo>(),
            onClickDeleteButton={
                viewModel.deleteList(it) },
            onCheckedChange ={ it:Todo, check:Boolean ->
                viewModel.updateToggle(it, check)
            }
        )
        adapter.setHasStableIds(true)
        binding.rvDone.adapter = adapter
        binding.rvDone.layoutManager = LinearLayoutManager(activity)

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.todoList.observe(viewLifecycleOwner, Observer{
            binding.rvDone.post(Runnable { adapter.setDoneData(it.filter { x->x.isDone }) })
        })
    }
}