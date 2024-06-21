package com.zain.e_voting

import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.zain.e_voting.data.response.base.BaseResponse
import com.zain.e_voting.databinding.ActivityFixVotingBinding
import com.zain.e_voting.ui.login.LoginViewModel
import com.zain.e_voting.ui.voting.VotingActivity
import com.zain.e_voting.ui.voting.VotingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FixVotingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFixVotingBinding
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var votingViewModel: VotingViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        votingViewModel = ViewModelProvider(this)[VotingViewModel::class.java]
        binding = ActivityFixVotingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnFixYes.setOnClickListener {
            setResult()
        }
        binding.btnFixNo.setOnClickListener {
            finish()
        }


    }

    private fun setResult() {
        val id = intent.getStringExtra("paslon_id")
        loginViewModel.getNipd().observe(this) { nipd ->
            if (id != null && nipd != null && nipd.toString().isNotEmpty()) {
                votingViewModel.updateVoting(nipd, id)
            }
            Log.d(TAG, "setResult: $nipd")
        }

        votingViewModel.updateResult.observe(this) {
            when (it) {

                is BaseResponse.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is BaseResponse.Success -> {
                    binding.progressBar.visibility = View.GONE

                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("Success")
                    builder.setMessage(it.data?.message)

                    builder.setPositiveButton("OK") { dialog, _ ->
                        loginViewModel.removeNipd()
                        loginViewModel.removeIsLoginStatus()
                        val i = Intent(this, MainActivity::class.java)
                        i.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(i)
                        finish()
                    }
                    val dialog = builder.create()
                    dialog.show()
                }

                is BaseResponse.Error -> {
                    binding.progressBar.visibility = View.GONE
                    val builder = AlertDialog.Builder(this)
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

}