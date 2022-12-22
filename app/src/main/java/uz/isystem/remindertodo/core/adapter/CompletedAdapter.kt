package uz.isystem.remindertodo.core.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.isystem.remindertodo.core.model.Completed
import uz.isystem.remindertodo.databinding.ItemReminderComplBinding

class CompletedAdapter : RecyclerView.Adapter<CompletedAdapter.ViewHolder>() {

    private val data = ArrayList<Completed>()
    var onItemClicked: ((data: Completed) -> Unit)? = null


    fun setData(data: List<Completed>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemReminderComplBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindData(data: Completed) {

            binding.taskTitle.text = data.title
            binding.taskTime.text = data.date.toString()
            binding.taskBody.text = data.description

            binding.deleteBtn.setOnClickListener {
                onItemClicked?.invoke(data)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemReminderComplBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(data[position])


    }

    override fun getItemCount(): Int = data.size
}