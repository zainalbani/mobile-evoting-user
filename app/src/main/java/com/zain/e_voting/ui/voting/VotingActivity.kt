package com.zain.e_voting.ui.voting

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.zain.e_voting.FixVotingFragment
import com.zain.e_voting.R
import com.zain.e_voting.adapter.VotingAdapter
import com.zain.e_voting.data.local.KandidatData
import com.zain.e_voting.data.response.DataItem
import com.zain.e_voting.data.response.base.BaseResponse
import com.zain.e_voting.databinding.ActivityVotingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VotingActivity : AppCompatActivity(), VotingAdapter.ListPlaceInterface {
    private lateinit var binding: ActivityVotingBinding
    private val adapter = VotingAdapter(this)
    private lateinit var votingViewModel: VotingViewModel
    private var calonList: List<DataItem> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        votingViewModel = ViewModelProvider(this)[VotingViewModel::class.java]
        binding = ActivityVotingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setRecyclerView()


        binding.btnSubmit.setOnClickListener {
            val selectedPosition = adapter.getSelectedPosition()
            if (selectedPosition != -1) {
                val selectedCalon = calonList[selectedPosition]
                val bundle = Bundle().apply {
                    putString("paslon_id", selectedCalon.paslonId.toString())
                }
                val dialogFragment = FixVotingFragment()
                dialogFragment.arguments = bundle
                dialogFragment.show(supportFragmentManager, "dialog_fix")
            } else {
                Toast.makeText(this, "No Option Selected", Toast.LENGTH_SHORT).show()
            }



        }
    }

    private fun setRecyclerView() {
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
                    adapter.setData(it.data!!.data!!)
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

    override fun place(id: String) {

    }
}