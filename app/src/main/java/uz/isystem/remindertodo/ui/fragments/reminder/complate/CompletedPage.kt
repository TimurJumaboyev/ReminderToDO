package uz.isystem.remindertodo.ui.fragments.reminder.complate

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.isystem.remindertodo.R
import uz.isystem.remindertodo.core.adapter.CompletedAdapter
import uz.isystem.remindertodo.core.base.BaseFragment
import uz.isystem.remindertodo.databinding.PageCompletedBinding
import uz.isystem.remindertodo.ui.viewModel.ReminderViewModel
import uz.isystem.remindertodo.ui.viewModel.ReminderViewModelImp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CompletedPage : BaseFragment(R.layout.page_completed) {

    private val binding by viewBinding(PageCompletedBinding::bind)
    private val adapter by lazy { CompletedAdapter() }
    private val vm: ReminderViewModel by viewModels<ReminderViewModelImp>()

    override fun onCreated(view: View, savedInstanceState: Bundle?) {

        setState()
        observe()
        setListener()

    }

    private fun setListener() {

        binding.refresh.setOnRefreshListener {
            vm.getListCompleted()
        }

    }


    private fun observe() {

        vm.getListCompleted()

        vm.getDataCompleted().observe(viewLifecycleOwner) {
            adapter.setData(it)
            binding.refresh.isRefreshing = false
        }


    }

    private fun setState() {

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.setHasFixedSize(true)

        adapter.onItemClicked = {

            vm.deleteCompleted(it)
            vm.getListCompleted()

        }

    }

}