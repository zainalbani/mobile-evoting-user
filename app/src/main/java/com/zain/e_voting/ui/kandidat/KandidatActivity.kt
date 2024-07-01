package com.zain.e_voting.ui.kandidat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.zain.e_voting.adapter.KandidatAdapter
import com.zain.e_voting.data.response.DataItem
import com.zain.e_voting.data.response.base.BaseResponse
import com.zain.e_voting.databinding.ActivityKandidatBinding
import com.zain.e_voting.ui.voting.VotingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class KandidatActivity : AppCompatActivity(), KandidatAdapter.ListKandidatInterface {
    private lateinit var binding: ActivityKandidatBinding
    private val adapter = KandidatAdapter(this)
    private lateinit var votingViewModel: VotingViewModel
    private var calonList: List<DataItem> = emptyList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        votingViewModel = ViewModelProvider(this)[VotingViewModel::class.java]
        binding = ActivityKandidatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setRecycleView()
        setButton()


    }

    private fun setButton() {
        binding.backButton.setOnClickListener {
            finish()
        }
    }

    private fun setRecycleView() {
        votingViewModel.getAllCalon()
        votingViewModel.votingResult.observe(this) {
            when (it) {
                is BaseResponse.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is BaseResponse.Success -> {
                    binding.progressBar.visibility = View.GONE
                    calonList = (it.data!!.data ?: emptyList()) as List<DataItem>
                    binding.recyclerView.layoutManager = LinearLayoutManager(this)
                    binding.recyclerView.adapter = adapter
                    adapter.setData(it.data.data!!)
                }


                is BaseResponse.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, "Error: ${it.msg}", Toast.LENGTH_SHORT).show()
                }

                else -> {

                }
            }
        }
    }

    override fun kandidat(id: String) {
        val bundle = Bundle()
        bundle.putString("id", id)
        val dialogFragment = DetailKandidatFragment()
        dialogFragment.arguments = bundle
        dialogFragment.show(supportFragmentManager, "dialog_detail_kandidat")

    }
}