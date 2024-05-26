package com.zain.e_voting

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.zain.e_voting.adapter.VotingAdapter
import com.zain.e_voting.data.local.KandidatData
import com.zain.e_voting.databinding.ActivityVotingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VotingActivity : AppCompatActivity(), VotingAdapter.ListPlaceInterface {
    private lateinit var binding: ActivityVotingBinding
    private val list = ArrayList<KandidatData>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVotingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = VotingAdapter(this)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
        adapter.setData(list)

        binding.btnSubmit.setOnClickListener {
            val selectedPosition = adapter.getSelectedPosition()
            if (selectedPosition != -1){
                Toast.makeText(this, "Selected: ${list[selectedPosition].ketua}", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "No Option Selected", Toast.LENGTH_SHORT).show()
            }


//            val dialogFragment = FixVotingFragment()
//            dialogFragment.show(supportFragmentManager, "dialog_fix")
        }
        list.add(
            KandidatData(
                getDrawable(R.drawable.usopp),
                "Zain Al Bani Budi Prabowo",
                "Siapa saja lupa"
            )
        )
        list.add(
            KandidatData(
                getDrawable(R.drawable.chopperemon),
                "Setya Rizky Pradana",
                "Siapa saja lupa"
            )
        )
        list.add(
            KandidatData(
                getDrawable(R.drawable.robin),
                "Annisa Dian",
                "Siapa saja lupa"
            )
        )
    }
    override fun place(id: String) {

    }
}