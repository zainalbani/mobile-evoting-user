package com.zain.e_voting.ui.voting

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.zain.e_voting.adapter.VotingAdapter
import com.zain.e_voting.data.response.DataItem
import com.zain.e_voting.data.response.base.BaseResponse
import com.zain.e_voting.databinding.ActivityVotingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VotingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVotingBinding
    private val adapter = VotingAdapter()
    private lateinit var votingViewModel: VotingViewModel
    private var calonList: List<DataItem> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        votingViewModel = ViewModelProvider(this)[VotingViewModel::class.java]
        binding = ActivityVotingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setRecyclerView()

        val nipd = intent.getStringExtra("nipd")
        Log.d(TAG, "onCreate: $nipd")
        binding.btnSubmit.setOnClickListener {
            val selectedPosition = adapter.getSelectedPosition()
            if (selectedPosition != -1) {
                val selectedCalon = calonList[selectedPosition]
                val i = Intent(this, FixVotingActivity::class.java)
                i.putExtra("paslon_id", selectedCalon.paslonId.toString())
                i.putExtra("nipd", nipd)
                i.putExtra("ketua", selectedCalon.namaKetua.toString())
                i.putExtra("wakil", selectedCalon.namaWakilKetua.toString())
                startActivity(i)
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
}