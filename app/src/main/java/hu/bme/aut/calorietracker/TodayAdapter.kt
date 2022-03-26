package hu.bme.aut.calorietracker

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.calorietracker.data.today.CalorieItem
import hu.bme.aut.calorietracker.databinding.ItemTodayBinding

class TodayAdapter(private val listener: OnCalorieItemDeletedListener) : RecyclerView.Adapter<TodayAdapter.TodayViewHolder>() {
    private val calories: MutableList<CalorieItem> = ArrayList()

    interface OnCalorieItemDeletedListener {
        fun onCalorieItemDeleted(item: CalorieItem, position : Int)
        fun onItemsUpdated()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodayViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_today, parent, false)
        return TodayViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodayViewHolder, position: Int) {
        val item = calories[position]
        holder.bind(item, position)
    }

    override fun getItemCount(): Int = calories.size

    fun addCalorieItem(newCalorieItem: CalorieItem) {
        calories.add(newCalorieItem)
        listener.onItemsUpdated()
        notifyItemInserted(calories.size - 1)
    }

    fun update(items: List<CalorieItem>) {
        calories.clear()
        calories.addAll(items)
        listener.onItemsUpdated()
        notifyDataSetChanged()
    }

    fun removeCalorieItem(position: Int) {
        calories.removeAt(position)
        listener.onItemsUpdated()
        notifyItemRemoved(position)
        if (position < calories.size) {
            notifyItemRangeChanged(position, calories.size - position)
        }
    }

    fun getTotalCalories(): Int {
        var sum: Int = 0
        for (c in calories){
            sum+= c.amount
        }
        return sum
    }

    fun allCaloriesDeleted(){
        calories.clear()
        notifyDataSetChanged()
    }

    inner class TodayViewHolder(private val itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding = ItemTodayBinding.bind(itemView)
        var item: CalorieItem? = null

        init {
            binding.root.setOnClickListener { /*listener.onCalorieItemDeleted(item)*/ }
        }

        fun bind(newCalorieItem: CalorieItem, position: Int) {
            item = newCalorieItem
            binding.TodayItemNameTextView.text = newCalorieItem.label

            binding.TodayItemAmountTextView.text = newCalorieItem.amount.toString()
            binding.TodayItemAmountTextView.setTextColor(if (newCalorieItem.calorieType == CalorieItem.CalorieType.INTAKE)  Color.RED else Color.GREEN )
            binding.calorieTypeIcon.setImageResource(if (newCalorieItem.calorieType == CalorieItem.CalorieType.INTAKE) R.drawable.diet else R.drawable.exercise)

            binding.TodayItemRemoveButton.setOnClickListener { listener.onCalorieItemDeleted(newCalorieItem, position)/*removeCalorieItem(position)*/ }

        }
    }
}