package com.zain.e_voting.ui.register

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.zain.e_voting.data.response.base.BaseResponse
import com.zain.e_voting.databinding.ActivityRegisterBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterActivity : BottomSheetDialogFragment() {
    private var _binding: ActivityRegisterBinding? = null
    private val binding get() = _binding!!
    private lateinit var registerViewModel: RegisterViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        registerViewModel = ViewModelProvider(this)[RegisterViewModel::class.java]
        _binding = ActivityRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setEditText()
        binding.btnRegister.setOnClickListener {
            val nipd = binding.etNipd.text.toString()
            val email = binding.etEmailRegister.text.toString()
            registerViewModel.registerUser(nipd, email)
            registerResult()
        }


    }

    private fun setButtonText() {
        binding.btnRegister.isEnabled =
            binding.etNipd.text != null && binding.etEmailRegister.text != null && binding.etNipd.text.toString()
                .isNotEmpty() && binding.etEmailRegister.text.toString().isNotEmpty()
    }

    private fun setEditText() {
        binding.etNipd.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                setButtonText()
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
        binding.etEmailRegister.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                setButtonText()
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
    }

    private fun registerResult() {

        registerViewModel.registerResult.observe(viewLifecycleOwner) {
            when (it) {
                is BaseResponse.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is BaseResponse.Success -> {
                    binding.progressBar.visibility = View.GONE

                    val email = binding.etEmailRegister.text.toString()
                    val verificationFragment = VerificationFragment()
                    val bundle = Bundle()
                    bundle.putString("email", email)
                    verificationFragment.arguments = bundle
                    verificationFragment.show(childFragmentManager, "verif_dialog")

                }

                is BaseResponse.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), "Error: ${it.msg}", Toast.LENGTH_SHORT).show()
                }

                else -> {

                }
            }
        }
    }
}