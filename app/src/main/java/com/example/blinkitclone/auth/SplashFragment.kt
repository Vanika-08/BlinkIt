package com.example.blinkitclone.auth

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.blinkitclone.R
import com.example.blinkitclone.activity.UsersMainActivity
import com.example.blinkitclone.databinding.FragmentSplashBinding
import com.example.blinkitclone.viewmodels.AuthViewModel
import kotlinx.coroutines.launch

class SplashFragment : Fragment() {

    private val viewModel: AuthViewModel by viewModels()
    private lateinit var binding: FragmentSplashBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentSplashBinding.inflate(layoutInflater)
        setStatusBarColar()

        Handler(Looper.getMainLooper()).postDelayed({
            lifecycleScope.launch {
                viewModel.isACurrentUser.collect{ isUserLoggedIn ->
                    if(isAdded){
                        if(isUserLoggedIn){
                            startActivity(Intent(requireActivity(), UsersMainActivity::class.java))
                            requireActivity().finish()
                        }else{
                            try {
                                val navController = findNavController()
                                if(navController.currentDestination?.id == R.id.splashFragment){
                                    navController.navigate(R.id.action_splashFragment_to_signInFragment)
                                }
                            }catch (e:Exception){
                                e.printStackTrace()
                            }
                        }
                    }
                }
            }
        }, 2000)

        return binding.root
    }

    private fun setStatusBarColar() {
        activity?.window?.apply {
            val statusBarColors = ContextCompat.getColor(requireContext(), R.color.yellow)
            statusBarColor = statusBarColors
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
        }
    }
}