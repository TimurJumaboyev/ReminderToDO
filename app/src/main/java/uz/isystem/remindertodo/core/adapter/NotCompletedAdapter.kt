package uz.isystem.remindertodo.core.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import uz.isystem.remindertodo.core.model.NotCompleted
import uz.isystem.remindertodo.databinding.ItemReminderBinding
import uz.isystem.remindertodo.ui.fragments.add.AddScreen
import uz.isystem.remindertodo.ui.fragments.reminder.notcompleted.NotCompletedPageDirections
import java.text.SimpleDateFormat

class NotCompletedAdapter : RecyclerView.Adapter<NotCompletedAdapter.ViewHolder>() {

    private val data = ArrayList<NotCompleted>()

    var onItemClicked: ((data: NotCompleted) -> Unit)? = null
    private var onclick: OnSentMyClick? = null

    fun setMyClick(onclick: OnSentMyClick) {
        this.onclick = onclick
    }


    fun setData(data: List<NotCompleted>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemReminderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindData(data: NotCompleted) {


            binding.taskTitle.text = data.title
            binding.taskTime.text = data.date
            binding.taskBody.text = data.description
            binding.checkBtn.setOnClickListener {
                onItemClicked?.invoke(data)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemReminderBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bindData(data[position])

        holder.itemView.setOnClickListener {
            onclick?.onMyClick(data[position])
        }
    }

    override fun getItemCount(): Int = data.size

    interface OnSentMyClick {
        fun onMyClick(data: NotCompleted)
    }
}