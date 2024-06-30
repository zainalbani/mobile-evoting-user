package com.zain.e_voting

import android.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.zain.e_voting.data.response.base.BaseResponse
import com.zain.e_voting.databinding.ActivityLoginBinding
import com.zain.e_voting.databinding.ActivityRegisterBinding
import com.zain.e_voting.ui.RegisterViewModel
import com.zain.e_voting.ui.voting.VotingViewModel
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
        binding.btnRegister.setOnClickListener {
            val verificationFragment = VerificationFragment()
            verificationFragment.show(childFragmentManager, "verif_dialog")
        }
        searchUser()

    }

    private fun searchUser() {

        registerViewModel.searchUserResult.observe(viewLifecycleOwner) {
            when (it) {
                is BaseResponse.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is BaseResponse.Success -> {
                    binding.progressBar.visibility = View.GONE
                    val listNama = it.data?.data?.map { it?.namaSiswa} ?: listOf()

                    
                    val adapter = ArrayAdapter(requireContext(), R.layout.simple_dropdown_item_1line, listNama)
                    binding.etDefaultKelas.setAdapter(adapter)



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