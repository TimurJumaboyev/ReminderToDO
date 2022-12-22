package uz.isystem.remindertodo.core.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import uz.isystem.remindertodo.core.model.ReminderTableModel
import uz.isystem.remindertodo.databinding.ItemReminderBinding
import uz.isystem.remindertodo.ui.fragments.reminder.ReminderScreenDirections

class ReminderAdapter : RecyclerView.Adapter<ReminderAdapter.ViewHolder>() {

    private val data = ArrayList<ReminderTableModel>()

    fun setData(data: List<ReminderTableModel>) {
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemReminderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindData(data: ReminderTableModel) {

            binding.taskTitle.text = data.title
            binding.taskTime.text = data.date
            binding.taskBody.text = data.description



        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemReminderBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(!data[position].isComplated){
            holder.bindData(data[position])
        }
    }

    override fun getItemCount(): Int = data.size
}