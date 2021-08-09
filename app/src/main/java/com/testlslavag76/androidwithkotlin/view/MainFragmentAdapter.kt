package com.testlslavag76.androidwithkotlin.view

import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.widget.Toast
import com.testlslavag76.androidwithkotlin.databinding.MainRecyclerItemBinding
import com.testlslavag76.androidwithkotlin.model.data.Weather

class MainFragmentAdapter :
    RecyclerView.Adapter<MainFragmentAdapter.MainViewHolder>() {

    private var weatherData: List<Weather> = listOf()

    private var onItemViewClickListener: (Weather) -> Unit = {}

    fun setOnItemViewClickListener(onItemViewClickListener: (Weather) -> Unit) {
        this.onItemViewClickListener = onItemViewClickListener
    }

//    fun removeOnItemViewClickListener() {
//        onItemViewClickListener = null
//    }

    fun setWeather(data: List<Weather>) {
        weatherData = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding = MainRecyclerItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(weatherData[position])
    }

    override fun getItemCount(): Int {
        return weatherData.size
    }

    inner class MainViewHolder(val binding: MainRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(weather: Weather) {
            binding.apply {
                mainFragmentRecyclerItemTextView.text = weather.city.city
                root.setOnClickListener {
                    onItemViewClickListener(weather)
                }
            }
        }
    }
}