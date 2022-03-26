package hu.bme.aut.calorietracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import hu.bme.aut.calorietracker.data.CalorieTrackerDatabase
import hu.bme.aut.calorietracker.data.day.Day
import hu.bme.aut.calorietracker.databinding.ActivityStatisticsBinding
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.thread

class StatisticsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStatisticsBinding
    private lateinit var adapter: StatisticsAdapter

    private lateinit var database: CalorieTrackerDatabase

    private lateinit var todayDate : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityStatisticsBinding.inflate(layoutInflater)
        setContentView(binding.root)



        val sdf = SimpleDateFormat("dd/MM/yyyy")
        todayDate = sdf.format( Date())

        database = CalorieTrackerDatabase.getDatabase(applicationContext)


        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.mainRecyclerViewStat.layoutManager = LinearLayoutManager(this)
        adapter = StatisticsAdapter()

        binding.mainRecyclerViewStat.adapter = adapter

        loadItemsInBackground()
    }

    private fun loadItemsInBackground() {
        thread {
            val items = database.calorieItemDao().getAll()
            //Log.d(items.toString(), "kaloriak")
            var days : MutableList<Day> = ArrayList()
            days.add(Day(todayDate,0))
            for(item in items){
                val itemsDate = item.date
                //Log.d(itemsDate, "datuma")
                var pos : Int? = null
                for(d in days){
                    //Log.d(itemsDate.toString(), d.date)
                    if(d.date .equals(itemsDate)){
                        pos = days.indexOf(d)
                    }
                }

                if(pos == null){
                    days.add(Day(itemsDate,item.amount))
                }
                else{
                    val old = days[pos]
                    days[pos] = Day(old.date, (old.sum + item.amount))
                }

            }

            days.sortBy { it.date }
            //var days : MutableList<Day> = ArrayList()
            //days.add(Day("teszt", 123))

            runOnUiThread {

                adapter.update(days)
            }
        }
    }
}