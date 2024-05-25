package com.zain.e_voting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.zain.e_voting.databinding.ActivityKandidatBinding

class KandidatActivity : AppCompatActivity() {
    private lateinit var binding : ActivityKandidatBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKandidatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.cardPaslon1.setOnClickListener {
            val dialogFragment = DetailKandidatFragment()
            dialogFragment.show(supportFragmentManager,"dialog_register")
        }
    }
}