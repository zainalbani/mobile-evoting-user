package com.zain.e_voting.ui.voting

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.zain.e_voting.MainActivity
import com.zain.e_voting.data.response.base.BaseResponse
import com.zain.e_voting.databinding.ActivityHasilVotingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HasilVotingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHasilVotingBinding
    private lateinit var votingViewModel: VotingViewModel
    private var jumlahList: List<Int> = emptyList()
    private var paslonList: List<Int> = emptyList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        votingViewModel = ViewModelProvider(this)[VotingViewModel::class.java]
        binding = ActivityHasilVotingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        hasilResult()
        setButton()

    }

    private fun setButton() {
        binding.btnToMain.setOnClickListener {
            val i = Intent(this, MainActivity::class.java)
            i.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(i)
            finish()
        }
    }

    private fun hasilResult() {
        votingViewModel.getAllHasil()
        votingViewModel.hasilResult.observe(this) {
            when (it) {

                is BaseResponse.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is BaseResponse.Success -> {
                    binding.progressBar.visibility = View.GONE

                    jumlahList = it.data?.data?.map { it?.jumlahSuara } as List<Int>? ?: emptyList()
                    paslonList = it.data?.data?.map { it?.paslonId } as List<Int>? ?: emptyList()
                    setupPieChart()
                    loadPieChartData()
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

    private fun setupPieChart() {
        binding.pieChart.isDrawHoleEnabled = true
        binding.pieChart.setHoleColor(Color.WHITE)
        binding.pieChart.setEntryLabelColor(Color.BLACK)
        binding.pieChart.setEntryLabelTextSize(12f)
        binding.pieChart.description.isEnabled = false
    }

    private fun loadPieChartData() {
//        val entries = listOf(
//            PieEntry(100f, "Option 1"),
//            PieEntry(140f, "Option 2"),
//            PieEntry(250f, "Option 3"),
//            PieEntry(200f, "Option 4"),
//            PieEntry(200f, "Option 5")
//        )
        val entries = jumlahList.mapIndexed { index, jumlahSuara ->
            val paslonId = paslonList.getOrNull(index) ?: 0
            PieEntry(jumlahSuara.toFloat(), "paslon $paslonId")
        }
        val colors = generateColors(entries.size)
        val dataSet = PieDataSet(entries, "Voting Results")
        dataSet.colors = colors
        dataSet.valueTextColor = Color.BLACK
        dataSet.valueTextSize = 16f

        val data = PieData(dataSet)
        binding.pieChart.data = data
        binding.pieChart.invalidate()
    }
    private fun generateColors(entryCount: Int): List<Int> {
        val baseColors = listOf( Color.RED, Color.GREEN, Color.YELLOW, Color.CYAN, Color.MAGENTA)
        val colors = mutableListOf<Int>()
        var index = 0
        while (colors.size < entryCount) {
            colors.add(baseColors[index % baseColors.size])
            index++
        }
        return colors
    }
}