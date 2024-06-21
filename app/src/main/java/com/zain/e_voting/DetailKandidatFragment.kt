package com.zain.e_voting

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.zain.e_voting.data.response.base.BaseResponse
import com.zain.e_voting.databinding.FragmentDetailKandidatBinding
import com.zain.e_voting.ui.kandidat.KandidatViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailKandidatFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentDetailKandidatBinding? = null
    private val binding get() = _binding!!
    private lateinit var kandidatViewModel: KandidatViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        kandidatViewModel = ViewModelProvider(this)[KandidatViewModel::class.java]
        _binding = FragmentDetailKandidatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setResult()
    }

    private fun setResult() {
        val id = arguments?.getString("id")
        if (id != null) {
            kandidatViewModel.getCalonById(id)
        }
        kandidatViewModel.kandidatResult.observe(this) {
            when (it) {
                is BaseResponse.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is BaseResponse.Success -> {
                    binding.progressBar.visibility = View.GONE
                    val namaKetua = it.data?.data?.namaKetua.toString()
                    val namaWakil = it.data?.data?.namaWakilKetua.toString()
                    val paslonId = it.data?.data?.paslonId.toString()
                    val visi = it.data?.data?.visi.toString()
                    val misi = it.data?.data?.misi.toString()
                    val youtubeLink = it.data?.data?.youtubeLink.toString()
                    Glide.with(requireContext())
                        .load(it.data?.data?.imageUrl)
                        .into(binding.ivProfileKandidat)
                    binding.tvNamaKetua.text = namaKetua
                    binding.tvNamaWakil.text = namaWakil
                    binding.tvVisi.text = visi
                    binding.tvMisi.text = misi
                    binding.tvPaslonId.text = "Paslon $paslonId"
                    binding.tvYoutubeLink.setOnClickListener {
                        val youtubeIntent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
                        startActivity(youtubeIntent)
                    }
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