package com.zain.e_voting

import android.app.AlertDialog
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
import com.zain.e_voting.data.response.base.BaseResponse
import com.zain.e_voting.databinding.FragmentFixVotingBinding
import com.zain.e_voting.databinding.FragmentVerificationBinding
import com.zain.e_voting.ui.login.LoginViewModel
import com.zain.e_voting.ui.voting.VotingViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FixVotingFragment : DialogFragment() {
    private var _binding: FragmentFixVotingBinding? = null
    private val binding get() = _binding!!
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var votingViewModel: VotingViewModel
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
        votingViewModel = ViewModelProvider(this)[VotingViewModel::class.java]
        _binding = FragmentFixVotingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnFixYes.setOnClickListener {
            setResult()
        }
        binding.btnFixNo.setOnClickListener {
            dismiss()
        }
    }

    private fun setResult() {
        val id = arguments?.getString("paslon_id")
        loginViewModel.getNipd().observe(viewLifecycleOwner){nipd ->
            if (id != null) {
                votingViewModel.updateVoting(nipd, id)
            }
        }

        votingViewModel.updateResult.observe(this) {
            when (it) {

                is BaseResponse.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is BaseResponse.Success -> {
                    binding.progressBar.visibility = View.GONE

                    val builder = AlertDialog.Builder(requireContext())
                    builder.setTitle("Success")
                    builder.setMessage(it.data?.message)

                    builder.setPositiveButton("OK") { _, _ ->

                        loginViewModel.removeToken()
                        loginViewModel.removeIsLoginStatus()
                        loginViewModel.removeNipd()
                        val intent = Intent(requireContext(), MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                    }
                    val dialog = builder.create()
                    dialog.show()
                }

                is BaseResponse.Error -> {
                    binding.progressBar.visibility = View.GONE
                    val builder = AlertDialog.Builder(requireContext())
                    builder.setTitle("Error")
                    builder.setMessage(it.msg)

                    builder.setPositiveButton("OK") { _, _ ->

                    }
                    val dialog = builder.create()
                    dialog.show()
                }

                else -> {
                }
            }
        }
    }

    fun Int.toPx(requireContext: Context): Int =
        (this * requireContext.resources.displayMetrics.density).toInt()
}