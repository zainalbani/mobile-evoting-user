package com.zain.e_voting

import android.graphics.Color
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.zain.e_voting.databinding.ActivityHasilVotingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HasilVotingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHasilVotingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHasilVotingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupPieChart()
        loadPieChartData()
    }
    private fun setupPieChart() {
        binding.pieChart.isDrawHoleEnabled = true
        binding.pieChart.setHoleColor(Color.WHITE)
        binding.pieChart.setEntryLabelColor(Color.BLACK)
        binding.pieChart.setEntryLabelTextSize(12f)
        binding.pieChart.description.isEnabled = false
    }

    private fun loadPieChartData() {
        val entries = listOf(
            PieEntry(100f, "Option 1"),
            PieEntry(140f, "Option 2"),
            PieEntry(250f, "Option 3"),
            PieEntry(200f, "Option 4"),
            PieEntry(200f, "Option 5")
        )
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