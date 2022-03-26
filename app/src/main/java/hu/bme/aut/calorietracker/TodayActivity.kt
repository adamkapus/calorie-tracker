package hu.bme.aut.calorietracker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import hu.bme.aut.calorietracker.calorie.ExerciseManager
import hu.bme.aut.calorietracker.data.today.CalorieItem
import hu.bme.aut.calorietracker.data.CalorieTrackerDatabase
import hu.bme.aut.calorietracker.databinding.ActivityTodayBinding
import hu.bme.aut.calorietracker.fragments.AddCalorieIntakeFragment
import hu.bme.aut.calorietracker.fragments.AddExerciseFragment
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.thread

class TodayActivity : AppCompatActivity(), TodayAdapter.OnCalorieItemDeletedListener, AddCalorieIntakeFragment.AddCalorieIntakeListener, AddExerciseFragment.AddExerciseListener {

    private lateinit var binding: ActivityTodayBinding
    private lateinit var adapter: TodayAdapter
    private lateinit var exerciseMan: ExerciseManager

    private lateinit var database: CalorieTrackerDatabase

    private lateinit var todayDate : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTodayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        exerciseMan = ExerciseManager()

        val sdf = SimpleDateFormat("dd/MM/yyyy")
        todayDate = sdf.format( Date())

        database = CalorieTrackerDatabase.getDatabase(applicationContext)

        //manageDays()

        initRecyclerView()
        initCalorieIntakeFab()
        initExerciseFab()

    }

    /*private fun manageDays(){

        var sdf = SimpleDateFormat("dd/MM/yyyy");
        var todayDate = sdf.format( Date());
        Log.d("mai datum",todayDate)
        //val today = Day(null, date, )
        thread {
            val ma: Day? = database.dayDao().findByDate(todayDate)
            if (ma == null) {
                Log.d("NIIIIIIIINCS", "null vok")
                database.dayDao().insert(Day(null,todayDate,0))
                database.calorieItemDao().deleteAll()
                //ad
            } else {
                Log.d("VAAAN", todayDate.toString())
            }
            logDays()
        }

        //database.calorieItemDao().insert(newCalorieItem)
    }*/

    /*private fun logDays(){
        thread {
            val osszes = database.dayDao().getAll()
            Log.d("Összses", osszes.toString())
        }
    }*/

    private fun initCalorieIntakeFab() {
        binding.intakefab.setOnClickListener {
            AddCalorieIntakeFragment().show(supportFragmentManager, AddCalorieIntakeFragment::class.java.simpleName)
        }
    }

    private fun initExerciseFab() {
        binding.exercisefab.setOnClickListener {
            AddExerciseFragment().show(supportFragmentManager, AddExerciseFragment::class.java.simpleName)
        }
    }

    private fun initRecyclerView() {
        binding.mainRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter = TodayAdapter(this)

        binding.mainRecyclerView.adapter = adapter

        loadItemsInBackground()
    }

    private fun loadItemsInBackground() {
        thread {
            val items = database.calorieItemDao().getByDate(todayDate)
            runOnUiThread {
                adapter.update(items)
            }
        }
    }

    private fun addCalorieItem(newCalorieItem: CalorieItem){
        thread {
            database.calorieItemDao().insert(newCalorieItem)

            runOnUiThread {
                adapter.addCalorieItem(newCalorieItem)
                //onItemsUpdated()
            }
            //onItemsUpdated()
        }
    }

    override fun onCalorieIntakeAdded(label: String?, amount : Int?) {
        if(amount == null){
            Snackbar.make(binding.root, R.string.dialog_new_calorie_amount_not_given, Snackbar.LENGTH_SHORT).show()
        }
        else{
            //binding.calorieSumValue.text = (adapter.getTotalCalories() + amount).toString()
            //adapter.addCalorieItem( CalorieItem(CalorieItem.CalorieType.INTAKE, label!!, amount))
            //var newcalorieItem= CalorieItem(null,CalorieItem.CalorieType.INTAKE, label!!, amount )
            addCalorieItem(CalorieItem(null, CalorieItem.CalorieType.INTAKE, label!!, amount, todayDate))
            //onItemsUpdated()
        }
    }

    override fun onExerciseAdded(exerciseName: String, label: String?, time :Int?) {

        if(time == null){
            Snackbar.make(binding.root, R.string.dialog_exercise_time_not_given, Snackbar.LENGTH_SHORT).show()
        }
        else{
            val  burntCalories = exerciseMan.getBurntCaloriesOfExercise(exerciseName, time)
           // binding.calorieSumValue.text = (adapter.getTotalCalories() + burntCalories).toString()
            //adapter.addCalorieItem(CalorieItem(CalorieType.EXERCISE, label!!, burntCalories ))
            addCalorieItem(
                CalorieItem(null,
                    CalorieItem.CalorieType.EXERCISE, label!!, burntCalories, todayDate )
            )
            //onItemsUpdated()
        }

    }

    override fun onCalorieItemDeleted(item: CalorieItem, position : Int) {
        thread {
            database.calorieItemDao().deleteItem(item)

            runOnUiThread {
                adapter.removeCalorieItem(position)
            }
        }
    }

    override fun onItemsUpdated() {
        val totalCalories = adapter.getTotalCalories()
        binding.calorieSumValue.text = (totalCalories).toString()
        /*thread {
            //val totalCalories = adapter.getTotalCalories()
            //binding.calorieSumValue.text = (totalCalories).toString()

            /*var sdf = SimpleDateFormat("dd/MM/yyyy");
            var todayDate = sdf.format( Date());

            database.dayDao().updateSum(todayDate,totalCalories)*/

            //logDays()
        }*/

    }


}