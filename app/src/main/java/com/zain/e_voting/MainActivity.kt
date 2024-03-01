package com.zain.e_voting

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
    }
}