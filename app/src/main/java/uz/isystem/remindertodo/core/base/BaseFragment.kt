package uz.isystem.remindertodo.core.base

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import uz.isystem.remindertodo.R

@AndroidEntryPoint
abstract class BaseFragment(@LayoutRes view: Int) : Fragment(view) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onCreated(view, savedInstanceState)

    }

    abstract fun onCreated(view: View, savedInstanceState: Bundle?)


}