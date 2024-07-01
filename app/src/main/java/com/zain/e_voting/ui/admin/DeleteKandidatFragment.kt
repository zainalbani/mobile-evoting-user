package com.zain.e_voting.ui.admin

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.zain.e_voting.R
import com.zain.e_voting.data.response.base.BaseResponse
import com.zain.e_voting.databinding.FragmentDeleteKandidatBinding
import com.zain.e_voting.ui.voting.VotingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeleteKandidatFragment : Fragment() {
    private var _binding: FragmentDeleteKandidatBinding? = null
    private val binding get() = _binding!!
    private lateinit var adminViewModel: AdminViewModel
    private lateinit var votingViewModel: VotingViewModel
    private var jumlah = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        adminViewModel = ViewModelProvider(this)[AdminViewModel::class.java]
        votingViewModel = ViewModelProvider(this)[VotingViewModel::class.java]
        _binding = FragmentDeleteKandidatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setKandidatSize()
        setButton()
    }

    private fun setButton() {
        binding.btnDelete.setOnClickListener {
            adminViewModel.deleteKandidat(jumlah)
            adminViewModel.deleteResult.observe(viewLifecycleOwner) {
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
                            binding.btnDelete.isEnabled = false
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

    private fun setKandidatSize() {
        votingViewModel.getAllCalon()
        votingViewModel.votingResult.observe(viewLifecycleOwner) {
            when (it) {
                is BaseResponse.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is BaseResponse.Success -> {
                    binding.progressBar.visibility = View.GONE
                    jumlah = it.data?.data?.size.toString()

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