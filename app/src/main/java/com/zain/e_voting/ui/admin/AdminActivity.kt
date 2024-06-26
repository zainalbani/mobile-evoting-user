package com.zain.e_voting.ui.admin

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.zain.e_voting.MainActivity
import com.zain.e_voting.R
import com.zain.e_voting.databinding.ActivityAdminBinding
import com.zain.e_voting.ui.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdminActivity : AppCompatActivity() {
    private lateinit var binding : ActivityAdminBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setButton()

        setSupportActionBar(binding.appBarAdmin.toolbar)
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView

        val navController = findNavController(R.id.content_frame)

        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.nav_create, R.id.nav_update, R.id.nav_delete), drawerLayout
        )
        binding.appBarAdmin.toolbar.setupWithNavController(navController, drawerLayout)
        navView.setupWithNavController(navController)

        binding.navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_create -> {
                    navController.navigate(R.id.createKandidatFragment)
                }
                R.id.nav_update -> {
                    navController.navigate(R.id.updateKandidatFragment)
                }
                R.id.nav_delete -> {
                    navController.navigate(R.id.deleteKandidatFragment)
                }
            }
            binding.drawerLayout.closeDrawers()
            true
        }

    }

    private fun setButton() {
        binding.appBarAdmin.btnLogoutAdmin.setOnClickListener {
            loginViewModel.removeNipd()
            loginViewModel.removeIsLoginStatus()
            val i = Intent(this, MainActivity::class.java)
            i.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(i)
            finish()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.content_frame)
        return navController.navigateUp(appBarConfiguration)|| super.onSupportNavigateUp()
    }
}