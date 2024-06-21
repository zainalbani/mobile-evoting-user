package com.zain.e_voting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.zain.e_voting.databinding.ActivityMainBinding
import com.zain.e_voting.ui.login.LoginActivity
import com.zain.e_voting.ui.login.LoginViewModel
import com.zain.e_voting.ui.voting.VotingActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var loginViewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loginViewModel.getDataStoreIsLogin().observe(this) { isLogin ->
            if (isLogin == true) {
                val intent = Intent(this, VotingActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
            }
        }

        binding.btnLoginMain.setOnClickListener {
            val dialogFragment = LoginActivity()
            dialogFragment.show(supportFragmentManager, "dialog_login")
        }
        binding.btnRegisterMain.setOnClickListener {
            val dialogFragment = RegisterActivity()
            dialogFragment.show(supportFragmentManager, "dialog_register")
        }
        binding.btnKandidatMain.setOnClickListener {
            val intent = Intent(this, KandidatActivity::class.java)
            startActivity(intent)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        finish()
    }
}