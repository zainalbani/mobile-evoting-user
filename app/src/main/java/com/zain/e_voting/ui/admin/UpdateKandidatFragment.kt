package com.zain.e_voting.ui.admin

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.zain.e_voting.data.response.base.BaseResponse
import com.zain.e_voting.databinding.FragmentUpdateKandidatBinding
import com.zain.e_voting.ui.kandidat.KandidatViewModel
import com.zain.e_voting.ui.voting.VotingViewModel
import com.zain.e_voting.utils.reduceFileImage
import com.zain.e_voting.utils.uriToFile
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody

@AndroidEntryPoint
class UpdateKandidatFragment : Fragment() {
    private var _binding: FragmentUpdateKandidatBinding? = null
    private val binding get() = _binding!!
    private lateinit var adminViewModel: AdminViewModel
    private lateinit var votingViewModel: VotingViewModel
    private lateinit var kandidatViewModel: KandidatViewModel
    private var paslonId = ""
    private var images = ""

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
        kandidatViewModel = ViewModelProvider(this)[KandidatViewModel::class.java]
        _binding = FragmentUpdateKandidatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setKandidatList()
        setButton()
        setPermission()

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
                    val listPaslon = it.data?.data?.map { it?.paslonId } ?: emptyList()
                    val adapter = ArrayAdapter(
                        requireContext(),
                        android.R.layout.simple_spinner_item,
                        listPaslon
                    )
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

                    binding.etNomorKandidat.adapter = adapter
                    binding.etNomorKandidat.onItemSelectedListener =
                        object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(
                                parent: AdapterView<*>,
                                view: View,
                                position: Int,
                                id: Long
                            ) {
                                val selectedItem = parent.getItemAtPosition(position).toString()
                                paslonId = selectedItem
                                setDetailKandidat()
                            }

                            override fun onNothingSelected(parent: AdapterView<*>) {

                            }
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

    private fun setDetailKandidat() {
        kandidatViewModel.getCalonById(paslonId)
        kandidatViewModel.kandidatResult.observe(this) {
            when (it) {
                is BaseResponse.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is BaseResponse.Success -> {
                    binding.progressBar.visibility = View.GONE
                    val namaKetua = it.data?.data?.namaKetua.toString()
                    val namaWakil = it.data?.data?.namaWakilKetua.toString()
                    val visi = it.data?.data?.visi.toString()
                    val misi = it.data?.data?.misi.toString()
                    val link = it.data?.data?.youtubeLink.toString()
                    images = it.data?.data?.imageUrl.toString()
                    binding.etNamaKetua.setText(namaKetua)
                    binding.etNamaWakil.setText(namaWakil)
                    binding.etVisi.setText(visi)
                    binding.etMisi.setText(misi)
                    binding.etLink.setText(link)
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
        binding.btnSubmitUpdate.setOnClickListener {
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
            val id = paslonId.trim()
                .toRequestBody("multipart/form-data".toMediaType())
            currentImageUri?.let { uri ->
                val imageFile = uriToFile(uri, requireContext()).reduceFileImage()
                val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
                val imageMultipart = MultipartBody.Part.createFormData(
                    "image_url",
                    imageFile.name,
                    requestImageFile
                )
                if(binding.etImageAdd.text.toString().isNotEmpty()){
                    adminViewModel.updateKandidat(
                        paslonId, ketua, wakil, visi, misi, link, id, imageMultipart
                    )
                }
            }
            if(binding.etImageAdd.text.isEmpty()){
                val ketuaWithoutImage = binding.etNamaKetua.text.toString()
                val wakilWithoutImage = binding.etNamaWakil.text.toString()
                val visiWithoutImage = binding.etVisi.text.toString()
                val misiWithoutImage = binding.etMisi.text.toString()
                val linkWithoutImage = binding.etLink.text.toString()
                val idWithoutImage = paslonId
                adminViewModel.updateKandidatWithoutImage(idWithoutImage,ketuaWithoutImage,wakilWithoutImage,visiWithoutImage,misiWithoutImage,linkWithoutImage,idWithoutImage)
            }
            updateResult()
        }

    }

    private fun updateResult() {
        adminViewModel.updateResult.observe(viewLifecycleOwner) {
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