package uz.isystem.remindertodo.ui.fragments.reminder

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.tabs.TabLayoutMediator
import uz.isystem.remindertodo.R
import uz.isystem.remindertodo.core.adapter.ViewPagerAdapter
import uz.isystem.remindertodo.core.base.BaseFragment
import uz.isystem.remindertodo.databinding.ScreenReminderBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ReminderScreen : BaseFragment(R.layout.screen_reminder) {

    private val binding by viewBinding(ScreenReminderBinding::bind)
    private val tabNames: Array<String> = arrayOf(
        "Not Completed",
        "Completed",

    )


    override fun onCreated(view: View, savedInstanceState: Bundle?) {

        setState()
        setFloatButton()
        setListener()
        setTab()
      //  createNotificationChannel()
        binding.moreMenu.setOnClickListener {
            showPopup(binding.moreMenu)
        }
    }

    private fun showPopup(view: View) {
        val popup = PopupMenu(requireContext(), view)
        popup.inflate(R.menu.home_menu)

        popup.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item: MenuItem? ->

            when (item!!.itemId) {
                R.id.day -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
                R.id.night -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

                }
                R.id.auto -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                }
            }

            true
        })

        popup.show()
    }


    private fun setTab() {

        TabLayoutMediator(binding.tabLayaut, binding.viewPager) { tab, position ->
            tab.text = tabNames[position]
        }.attach()

    }

    private fun setState() {

        val adapter = ViewPagerAdapter(childFragmentManager, lifecycle)
        binding.viewPager.adapter = adapter

    }

    private fun setListener() {

        binding.floatButton.setOnClickListener {
            findNavController().navigate(ReminderScreenDirections.actionReminderScreenToAddScreen())
        }

    }

    private fun setFloatButton() {
        val color = Color.parseColor("#58D68D")
        binding.floatButton.backgroundTintList = ColorStateList.valueOf(color)
    }

    private fun createNotificationChannel() {
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel("to_do_list", "Tasks Notification Channel", importance).apply {
                description = "Notification for Tasks"
            }
        } else {
            TODO("VERSION.SDK_INT < O")
        }
        val notificationManager = activity?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }


}