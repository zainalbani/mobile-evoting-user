package com.zain.e_voting

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.zain.e_voting.databinding.FragmentFixVotingBinding
import com.zain.e_voting.databinding.FragmentVerificationBinding
import com.zain.e_voting.ui.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FixVotingFragment : DialogFragment() {
    private var _binding: FragmentFixVotingBinding? = null
    private val binding get() = _binding!!
    private lateinit var loginViewModel: LoginViewModel
    override fun onStart() {
        super.onStart()
        dialog!!.window
            ?.apply {
                setLayout(
                    350.toPx(requireContext()),
                    400.toPx(requireContext())
                )
                setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        _binding = FragmentFixVotingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnFixYes.setOnClickListener {
            loginViewModel.removeToken()
            loginViewModel.removeIsLoginStatus()
            val intent = Intent(requireContext(), MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
    }

    fun Int.toPx(requireContext: Context): Int =
        (this * requireContext.resources.displayMetrics.density).toInt()
}