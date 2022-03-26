package hu.bme.aut.calorietracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.calorietracker.data.day.Day
import hu.bme.aut.calorietracker.databinding.ItemDayBinding


class StatisticsAdapter() : RecyclerView.Adapter<StatisticsAdapter.StatisticsViewHolder>() {
    private val days: MutableList<Day> = ArrayList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatisticsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_day, parent, false)
        return StatisticsViewHolder(view)
    }

    override fun onBindViewHolder(holder: StatisticsViewHolder, position: Int) {
        val item = days[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = days.size

    fun addDayItem(newDayItem: Day) {
        days.add(newDayItem)
        //listener.onItemsUpdated()
        notifyItemInserted(days.size - 1)
    }

    fun update(dayItems: List<Day>) {
        days.clear()
        days.addAll(dayItems)
        //listener.onItemsUpdated()
        notifyDataSetChanged()
    }





    inner class StatisticsViewHolder(private val itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding = ItemDayBinding.bind(itemView)
        var item: Day? = null

        init {
            binding.root.setOnClickListener { /*listener.onCalorieItemDeleted(item)*/ }
        }

        fun bind(newDayItem: Day) {
            item = newDayItem
            binding.dayItemDate.text = newDayItem.date

            binding.dayItemSum.text = newDayItem.sum.toString()

        }
    }
}