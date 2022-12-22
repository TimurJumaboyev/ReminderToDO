package uz.isystem.remindertodo.core.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import uz.isystem.remindertodo.ui.fragments.reminder.complate.CompletedPage
import uz.isystem.remindertodo.ui.fragments.reminder.notcompleted.NotCompletedPage

class ViewPagerAdapter(
    fm: FragmentManager,
    lifecycle: Lifecycle,
) : FragmentStateAdapter(fm, lifecycle) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> NotCompletedPage()
            else -> CompletedPage()
        }
    }


}