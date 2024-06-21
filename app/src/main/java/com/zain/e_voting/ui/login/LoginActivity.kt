package com.zain.e_voting.ui.login

import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.zain.e_voting.RegisterActivity
import com.zain.e_voting.ui.voting.VotingActivity
import com.zain.e_voting.data.response.base.BaseResponse
import com.zain.e_voting.databinding.ActivityLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BottomSheetDialogFragment() {

    private var _binding: ActivityLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var loginViewModel: LoginViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        _binding = ActivityLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setButton()
        setEditText()
        loginResult()
    }

    private fun setButton() {
        binding.btnLogin.setOnClickListener {
            val nipd = binding.etUsernameLogin.text.toString()
            val otp = binding.etOtpLogin.text.toString()

            loginViewModel.loginUser(nipd = nipd, otp = otp)
        }
        binding.btnRegisterLog.setOnClickListener {
            val dialogFragment = RegisterActivity()
            dialogFragment.show(childFragmentManager, "dialog_register")
        }
    }
    private fun setButtonText() {
        binding.btnLogin.isEnabled =
            binding.etUsernameLogin.text != null && binding.etOtpLogin.text != null && binding.etUsernameLogin.text.toString()
                .isNotEmpty() && binding.etOtpLogin.text.toString().isNotEmpty()
    }
    private fun setEditText() {
        binding.etUsernameLogin.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                setButtonText()
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
        binding.etOtpLogin.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                setButtonText()
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
    }
    private fun loginResult() {
        loginViewModel.loginResult.observe(this) {
            when (it) {

                is BaseResponse.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is BaseResponse.Success -> {
                    binding.progressBar.visibility = View.GONE

                    val builder = AlertDialog.Builder(requireContext())
                    builder.setTitle("Success")
                    builder.setMessage(it.data?.message)

                    builder.setPositiveButton("OK") { dialog, _ ->
                        loginViewModel.saveIsLoginStatus(true)
                        loginViewModel.saveToken(it.data?.token.toString())
                        loginViewModel.saveNipd(it.data?.data?.nipd.toString())
                        val intent = Intent(requireContext(), VotingActivity::class.java)
                        intent.putExtra("nipd", it.data?.data?.nipd.toString())
                        Log.d(TAG, "loginResult: ${it.data?.data?.nipd.toString()}")
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
}