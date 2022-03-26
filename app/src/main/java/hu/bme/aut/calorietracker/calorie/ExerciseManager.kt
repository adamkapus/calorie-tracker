package hu.bme.aut.calorietracker.calorie

class ExerciseManager {

    fun getBurntCaloriesOfExercise( exerciseName : String, exerciseTime : Int): Int{
        when (exerciseName) {
            "Running" -> return (800 * exerciseTime/60)*-1
            "Walking" -> return (300 * exerciseTime/60)*-1
            "Swimming" -> return (500 * exerciseTime/60)*-1
            else -> {
               return 0
            }
        }
    }
}