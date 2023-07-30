package uz.isystem.remindertodo.ui.fragments.add

import android.Manifest
import android.app.AlarmManager
import android.app.AlertDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.graphics.Color
import android.nfc.Tag
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import dagger.hilt.android.AndroidEntryPoint
import uz.isystem.remindertodo.R
import uz.isystem.remindertodo.core.base.BaseFragment
import uz.isystem.remindertodo.core.model.NotCompleted
import uz.isystem.remindertodo.core.notification.AlarmReceiver
import uz.isystem.remindertodo.databinding.ScreenAddBinding
import uz.isystem.remindertodo.ui.fragments.reminder.ReminderScreen
import uz.isystem.remindertodo.ui.main.MainActivity
import uz.isystem.remindertodo.ui.viewModel.ReminderViewModel
import uz.isystem.remindertodo.ui.viewModel.ReminderViewModelImp
import java.util.*

@AndroidEntryPoint
class AddScreen : BaseFragment(R.layout.screen_add) {

    private val binding by viewBinding(ScreenAddBinding::bind)
    private val vm: ReminderViewModel by viewModels<ReminderViewModelImp>()
    private lateinit var picker: MaterialTimePicker
    private val CHANNEL_ID = "channel1"
    private var NOTIFICATION_ID = 123
    private val TAG = "AddScreen"


    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            Toast.makeText(requireContext(), "Permission granted", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "Permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setFloatButton()

        val type = requireArguments().getInt("type")

        if (type == 0) {
            setData()
        } else {
            setUpdateData()
        }
        binding.timeBtn.setOnClickListener {
            setTime()
        }
    }

    override fun onCreated(view: View, savedInstanceState: Bundle?) {

    }

    private fun setUpdateData() {
        val title1 = requireArguments().getString("title")
        val date1 = requireArguments().getString("date")
        val description1 = requireArguments().getString("description")
        val reminderId = requireArguments().getInt("id")

        binding.title.setText(title1)
        binding.description.setText(description1)
        binding.timeText.text = date1

        binding.doneBtn.setOnClickListener {
            val title = binding.title.text.toString()
            val description = binding.description.text.toString()
            val date = binding.timeText.text.toString()

            if (title.isEmpty()) {
                binding.title.error = "Write anything"
                return@setOnClickListener
            }

            if (description.isEmpty()) {
                binding.description.error = "Write anything"
                return@setOnClickListener
            }

            val data = NotCompleted(title, description, date)
            data.id = reminderId
            vm.update(data)
            findNavController().popBackStack()
        }
    }

    private fun checkPermissionOrStart() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(), Manifest.permission.SET_ALARM
            ) == PackageManager.PERMISSION_GRANTED -> {
                setTime()
            }
            shouldShowRequestPermissionRationale(Manifest.permission.SET_ALARM) -> {
                AlertDialog.Builder(requireContext())
                    .setTitle("Permission Request")
                    .setMessage("This permission is needed to set alarm")
                    .setPositiveButton("OK") { _, _ ->
                        requestPermissionLauncher.launch(Manifest.permission.SET_ALARM)
                    }
                    .setNegativeButton("Cancel") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .create()
                    .show()
            }
            else -> {
                requestPermissionLauncher.launch(Manifest.permission.SET_ALARM)
            }
        }
    }

    private fun createNotification(): NotificationCompat.Builder {
        val intent = Intent(requireContext(), ReminderScreen::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            requireContext(),
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val builder = NotificationCompat.Builder(requireContext(), CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_awesome)
            .setContentTitle("Reminder Application")
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setContentText(R.string.channel_description.toString())
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        return builder
    }

    private fun setTime() {
        picker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_24H)
            .setHour(0)
            .setMinute(0)
            .setTitleText("Select Alarm Time")
            .build()

        picker.show(parentFragmentManager, "notCompleted")

        picker.addOnPositiveButtonClickListener {
            if (picker.hour >= 0) {
                binding.timeText.text =
                    String.format("%02d", picker.hour) + ":" + String.format(
                        "%02d",
                        picker.minute
                    )
            }

            val calendar = Calendar.getInstance()
            calendar[Calendar.HOUR_OF_DAY] = picker.hour
            calendar[Calendar.MINUTE] = picker.minute
            setAlarm(calendar.timeInMillis)
        }
    }

    private fun setData() {
        binding.doneBtn.setOnClickListener {

            val title = binding.title.text.toString()
            val description = binding.description.text.toString()
            val date = binding.timeText.text.toString()

            if (title.isEmpty()) {
                binding.title.error = "Write anything"
                return@setOnClickListener
            }
            if (description.isEmpty()) {
                binding.description.error = "Write anything"
                return@setOnClickListener
            }
            val data = NotCompleted(title, description, date)
            vm.insertNotCompleted(data)

            Log.d(TAG,"$title,$description,$date")

            findNavController().popBackStack()

            Toast.makeText(requireContext(), "$title, $description,$date", Toast.LENGTH_SHORT).show()

            createNotificationChannel()
            val builder = createNotification()
            with(NotificationManagerCompat.from(requireContext())) {
                notify(NOTIFICATION_ID, builder.build())
            }
        }
    }

    private fun setFloatButton() {
        val color = Color.parseColor("#58D68D")
        binding.doneBtn.backgroundTintList = ColorStateList.valueOf(color)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, name, importance)
            channel.description = descriptionText
            channel.enableLights(true)
            channel.enableVibration(true)
            channel.setShowBadge(true)

            val notificationManager =
                requireContext().getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun setAlarm(timeInMillis: Long) {
        val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(requireContext(), AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            requireContext(),
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        alarmManager.setExact(
            AlarmManager.ELAPSED_REALTIME,
            timeInMillis,
            pendingIntent
        )
        Toast.makeText(requireContext(), "Alarm is set", Toast.LENGTH_SHORT).show()
    }
}