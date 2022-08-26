package com.example.todolist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist.databinding.FragmentAllBinding
import com.example.todolist.model.Todo
import com.example.todolist.ui.TodoViewModel

class AllFragment: Fragment() {

    private lateinit var viewModel: TodoViewModel
    private lateinit var binding: FragmentAllBinding
    private lateinit var adapter: TodoAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(requireActivity(), ViewModelProvider.NewInstanceFactory()) .get(TodoViewModel::class.java)
        binding = FragmentAllBinding.inflate(inflater, container, false)

        var allList = viewModel.allList.value
        adapter = TodoAdapter(
            allList?: emptyList<Todo>(),
            onClickDeleteButton={
                viewModel.deleteList(it) },
            onCheckedChange ={ it:Todo, check:Boolean ->
                viewModel.updateToggle(it, check)
            }
        )
        adapter.setHasStableIds(true)
        binding.rvAll.adapter = adapter
        binding.rvAll.layoutManager = LinearLayoutManager(activity)

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.todoList.observe(viewLifecycleOwner, Observer {
            binding.rvAll.post(Runnable { adapter.setallData(it) })
        })
    }


}