package hu.bme.aut.calorietracker.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatDialogFragment
import hu.bme.aut.calorietracker.R
import hu.bme.aut.calorietracker.databinding.DialogNewExerciseBinding

class AddExerciseFragment : AppCompatDialogFragment() {

    private lateinit var binding: DialogNewExerciseBinding
    private lateinit var listener: AddExerciseListener

    interface AddExerciseListener {
        fun onExerciseAdded(exerciseName: String,label: String?, time: Int?)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        binding = DialogNewExerciseBinding.inflate(LayoutInflater.from(context))

        val spinner: Spinner = binding.exercisesSpinner
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            context,
            R.array.exercises_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }


        try {
            listener = if (targetFragment != null) {
                targetFragment as AddExerciseListener
            } else {
                activity as AddExerciseListener
            }
        } catch (e: ClassCastException) {
            throw RuntimeException(e)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.dialog_exercise_title)
            .setView(binding.root)
            .setPositiveButton(R.string.ok_button) { _, _ ->
                //if(binding.ExerciseTime.text.toString().isNotEmpty()){
                    listener.onExerciseAdded(
                        binding.exercisesSpinner.selectedItem.toString(),
                        binding.NewExerciseName.text.toString(),
                        binding.ExerciseTime.text.toString().toIntOrNull()
                    )
                //}
            }
            .setNegativeButton(R.string.cancel_button, null)
            .create()
    }
}