package uz.isystem.remindertodo.ui.fragments.reminder.notcompleted

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.isystem.remindertodo.R
import uz.isystem.remindertodo.core.adapter.NotCompletedAdapter
import uz.isystem.remindertodo.core.base.BaseFragment
import uz.isystem.remindertodo.core.model.Completed
import uz.isystem.remindertodo.core.util.gone
import uz.isystem.remindertodo.core.util.visible
import uz.isystem.remindertodo.databinding.PageNotCompletedBinding
import uz.isystem.remindertodo.ui.viewModel.ReminderViewModel
import uz.isystem.remindertodo.ui.viewModel.ReminderViewModelImp
import dagger.hilt.android.AndroidEntryPoint
import uz.isystem.remindertodo.core.model.NotCompleted
import uz.isystem.remindertodo.core.notification.AlarmReceiver
import uz.isystem.remindertodo.ui.fragments.reminder.ReminderScreenDirections
import uz.isystem.remindertodo.ui.main.MainActivity

@AndroidEntryPoint
class NotCompletedPage : BaseFragment(R.layout.page_not_completed) {

    private val binding by viewBinding(PageNotCompletedBinding::bind)
    private val adapter by lazy { NotCompletedAdapter() }
    private val vm: ReminderViewModel by viewModels<ReminderViewModelImp>()

    override fun onCreated(view: View, savedInstanceState: Bundle?) {

        setState()
        observe()
        setListener()
    }

    private fun setListener() {

        binding.refresh.setOnRefreshListener {
            vm.getListNotCompleted()
        }

    }


    private fun observe() {

        vm.getListNotCompleted()

        vm.getDataNotCompleted().observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                binding.noElement.visible()
            }else{
                binding.noElement.gone()
            }
            adapter.setData(it)
            binding.refresh.isRefreshing = false
        }
        binding.root.setOnClickListener {
            adapter.onItemClicked={
                val data = NotCompleted(it.title, it.description, it.date)
                vm.update(data)
                val bundle = bundleOf(
                    "type" to 1
                )

                Navigation.findNavController(requireView()).navigate(R.id.addScreen, bundle)

            }

        }


    }

    private fun setState() {

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.setHasFixedSize(true)

        adapter.onItemClicked = {

            vm.deleteNotCompleted(it)
            val data = Completed(it.title, it.description, it.date)
            vm.insertCompleted(data)
            vm.getListNotCompleted()

        }
        adapter.setMyClick(object : NotCompletedAdapter.OnSentMyClick {
            override fun onMyClick(data: NotCompleted) {

                val bundle = bundleOf(

                    "type" to 2,
                    "title" to data.title.toString(),
                    "date" to data.date.toString(),
                    "description" to data.description.toString(),
                    "id" to data.id

                )

                Navigation.findNavController(requireView()).navigate(R.id.addScreen, bundle)

            }


            })
    }
}
