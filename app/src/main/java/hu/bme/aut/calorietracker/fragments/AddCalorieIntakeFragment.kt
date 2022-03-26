package hu.bme.aut.calorietracker.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatDialogFragment
import hu.bme.aut.calorietracker.R
import hu.bme.aut.calorietracker.databinding.DialogNewCalorieBinding

class AddCalorieIntakeFragment : AppCompatDialogFragment() {

    private lateinit var binding: DialogNewCalorieBinding
    private lateinit var listener: AddCalorieIntakeListener

    interface AddCalorieIntakeListener {
        fun onCalorieIntakeAdded(label: String?, amount : Int?)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        binding = DialogNewCalorieBinding.inflate(LayoutInflater.from(context))

        try {
            listener = if (targetFragment != null) {
                targetFragment as AddCalorieIntakeListener
            } else {
                activity as AddCalorieIntakeListener
            }
        } catch (e: ClassCastException) {
            throw RuntimeException(e)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle("Add calorie intake")
            .setView(binding.root)
            .setPositiveButton(R.string.ok_button) { _, _ ->

                //if(binding.NewCalorieAmount.text.toString().isNotEmpty()){
                    listener.onCalorieIntakeAdded(
                        binding.NewCalorieLabel.text.toString(),  binding.NewCalorieAmount.text.toString().toIntOrNull()
                    )
               // }
            }
            .setNegativeButton(R.string.cancel_button, null)
            .create()
    }
}
