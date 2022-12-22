package uz.isystem.remindertodo.ui.fragments.splash

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import uz.isystem.remindertodo.R
import uz.isystem.remindertodo.core.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@AndroidEntryPoint
@SuppressLint("CustomSplashScreen")
class SplashScreen : BaseFragment(R.layout.screen_splash) {


    override fun onCreated(view: View, savedInstanceState: Bundle?) {

        lifecycleScope.launch {

            delay(1500)
            findNavController().navigate(SplashScreenDirections.actionSplashScreenToReminderScreen())

        }

    }


}