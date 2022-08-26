package com.example.todolist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.todolist.databinding.FragmentMainBinding
import com.example.todolist.model.Todo
import com.example.todolist.ui.TodoViewModel
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment: Fragment() {

    private lateinit var viewModel: TodoViewModel
    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(requireActivity(), ViewModelProvider.NewInstanceFactory()) .get(TodoViewModel::class.java)
        binding = FragmentMainBinding.inflate(inflater, container, false)

        binding.vpTodo.adapter = activity?.let { ViewPagerFragmentStateAdapter(it) }
        binding.vpTodo.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        TabLayoutMediator(binding.tabLayout, binding.vpTodo){ tab, position ->
            tab.text = getTabTitle(position)
        }.attach()
        return binding.root

    }

    override fun onStart() {
        super.onStart()
        binding.button.setOnClickListener{
            val input = binding.input.text.toString()
            viewModel.addList(Todo(input,false))

            val transaction = (activity as MainActivity).supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frameLayout, HomeFragment())
            transaction.commit()

        }
    }
    private fun getTabTitle(position: Int): String? {
        return when (position) {
            0 -> "ALL"
            1 -> "ACTIVE"
            2 -> "DONE"
            else -> null
        }
    }
}