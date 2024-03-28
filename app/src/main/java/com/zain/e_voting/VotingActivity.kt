package com.zain.e_voting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.zain.e_voting.databinding.ActivityMainBinding
import com.zain.e_voting.databinding.ActivityVotingBinding

class VotingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVotingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVotingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSubmit.setOnClickListener {
            val dialogFragment = FixVotingFragment()
            dialogFragment.show(supportFragmentManager, "dialog_fix")
        }
    }
}