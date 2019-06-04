package hu.bme.aut.android.todo.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import hu.bme.aut.android.todo.R
import hu.bme.aut.android.todo.model.City
import kotlinx.android.synthetic.main.row_city.view.*
import android.text.method.TextKeyListener.clear
import android.view.MotionEvent





class SimpleItemRecyclerViewAdapter : RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>() {

    private val cityList = mutableListOf<City>()

    var itemClickListener: CityItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.row_city, parent, false)
        return ViewHolder(view)
    }
    fun getContext(): Context {
        return getContext()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val city = cityList[position]

        holder.city = city
        holder.tvTitle.text = city.name
    }

    fun getItem(pos: Int):City{
        return cityList[pos]
    }

    fun addItem(city: City) {
        val size = cityList.size
        cityList.add(city)
        notifyItemInserted(size)
    }

    fun addAll(citys: List<City>) {
        val size = cityList.size
        cityList += citys
        notifyItemRangeInserted(size, citys.size)
    }

    fun deleteRow(position: Int) {
        cityList.removeAt(position)
        notifyItemRemoved(position)
    }
    fun deleteAll(){
        cityList.clear()
        notifyDataSetChanged()
    }

    fun update(cities: List<City>) {
        cityList.clear()
        cityList.addAll(cities)
        notifyDataSetChanged()
    }

    override fun getItemCount() = cityList.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.ivPriority

        var city: City? = null

        init {
            itemView.setOnClickListener {
                city?.let { city -> itemClickListener?.onItemClick(city) }
            }

            itemView.setOnLongClickListener { view ->
                itemClickListener?.onItemLongClick(adapterPosition, view)
                true
            }

        }
    }

    interface CityItemClickListener {
        fun onItemClick(city: City)
        fun onItemLongClick(position: Int, view: View): Boolean
    }

}