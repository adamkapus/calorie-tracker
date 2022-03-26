package hu.bme.aut.calorietracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hu.bme.aut.calorietracker.databinding.ActivityLaunchBinding

class LaunchActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLaunchBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLaunchBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnToday.setOnClickListener {
            val todayIntent = Intent(this, TodayActivity::class.java)
            startActivity(todayIntent)
        }

        binding.btnStatistics.setOnClickListener {
            val statisticsIntent = Intent(this, StatisticsActivity::class.java)
            startActivity(statisticsIntent)
        }
    }
}