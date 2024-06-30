package com.zain.e_voting.ui.admin

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.zain.e_voting.data.response.base.BaseResponse
import com.zain.e_voting.databinding.FragmentCreateKandidatBinding
import com.zain.e_voting.ui.voting.VotingViewModel
import com.zain.e_voting.utils.reduceFileImage
import com.zain.e_voting.utils.uriToFile
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody

@AndroidEntryPoint
class CreateKandidatFragment : Fragment() {

    private var _binding: FragmentCreateKandidatBinding? = null
    private val binding get() = _binding!!
    private lateinit var adminViewModel: AdminViewModel
    private lateinit var votingViewModel: VotingViewModel

    private var currentImageUri: Uri? = null

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(requireContext(), "Permission Granted", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(requireContext(), "Permission Denied", Toast.LENGTH_LONG).show()
            }
        }

    private fun allPermissionsGranted() =
        ContextCompat.checkSelfPermission(
            requireContext(),
            REQUIRED_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        adminViewModel = ViewModelProvider(this)[AdminViewModel::class.java]
        votingViewModel = ViewModelProvider(this)[VotingViewModel::class.java]
        _binding = FragmentCreateKandidatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setPermission()
        setButton()
        setKandidatList()

    }

    private fun setKandidatList() {
        votingViewModel.getAllCalon()
        votingViewModel.votingResult.observe(viewLifecycleOwner) {
            when (it) {
                is BaseResponse.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is BaseResponse.Success -> {
                    binding.progressBar.visibility = View.GONE
                    val jumlah = it.data?.data?.size.toString()
                    binding.tvJumlah.text = "Jumlah Kandidat saat ini : $jumlah"
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

    private fun setButton() {
        binding.etImageAdd.setOnClickListener {
            openGallery()
        }
        binding.btnSubmitCreate.setOnClickListener {
            val ketua = binding.etNamaKetua.text.toString().trim()
                .toRequestBody("multipart/form-data".toMediaType())
            val wakil = binding.etNamaWakil.text.toString().trim()
                .toRequestBody("multipart/form-data".toMediaType())
            val visi = binding.etVisi.text.toString().trim()
                .toRequestBody("multipart/form-data".toMediaType())
            val misi = binding.etMisi.text.toString().trim()
                .toRequestBody("multipart/form-data".toMediaType())
            val link = binding.etLink.text.toString().trim()
                .toRequestBody("multipart/form-data".toMediaType())
            val paslonId = binding.etNomorKandidat.text.toString().trim()
                .toRequestBody("multipart/form-data".toMediaType())
            currentImageUri?.let { uri ->
                val imageFile = uriToFile(uri, requireContext()).reduceFileImage()
                val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
                val imageMultipart = MultipartBody.Part.createFormData(
                    "image_url",
                    imageFile.name,
                    requestImageFile
                )
                adminViewModel.createKandidat(
                    ketua,wakil,visi,misi,link,paslonId, imageMultipart
                )
            }
            createResult()
        }

    }

    private fun createResult() {
        adminViewModel.createResult.observe(viewLifecycleOwner) {
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

    private fun openGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri: Uri? ->
            if (uri != null) {
                currentImageUri = uri
                currentImageUri?.let {
                    val imageFile = uriToFile(uri, requireContext()).reduceFileImage()
                    binding.etImageAdd.setText(imageFile.name)
                }
            }
        }

    private fun setPermission() {
        if (!allPermissionsGranted()) {
            requestPermissionLauncher.launch(REQUIRED_PERMISSION)
        }
    }

    companion object {
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
    }


}