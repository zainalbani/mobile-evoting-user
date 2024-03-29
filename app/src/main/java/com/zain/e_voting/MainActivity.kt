package com.zain.e_voting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.zain.e_voting.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLoginMain.setOnClickListener {
            val dialogFragment = LoginActivity()
            dialogFragment.show(supportFragmentManager, "dialog_login")
        }
        binding.btnRegisterMain.setOnClickListener {
            val dialogFragment = RegisterActivity()
            dialogFragment.show(supportFragmentManager,"dialog_register")
        }
        binding.btnVotingMain.setOnClickListener {
            val intent = Intent(this, VotingActivity::class.java)
            startActivity(intent)
        }
    }
}