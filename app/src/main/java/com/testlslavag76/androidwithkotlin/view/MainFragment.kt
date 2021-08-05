package com.testlslavag76.androidwithkotlin.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import com.testlslavag76.androidwithkotlin.R
import com.testlslavag76.androidwithkotlin.databinding.MainFragmentBinding
import com.testlslavag76.androidwithkotlin.model.AppState
import com.testlslavag76.androidwithkotlin.model.data.Weather
import com.testlslavag76.androidwithkotlin.viewmodel.MainViewModel


class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private var _binding: MainFragmentBinding? = null
    private val binding
        get() = _binding!!

    private val adapter = MainFragmentAdapter()
    private var isDataSetRus: Boolean = true


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        adapter.removeOnItemViewClickListener()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter.setOnItemViewClickListener(object : OnItemViewClickListener {
            override fun onItemViewClick(weather: Weather) {
                val manager = activity?.supportFragmentManager
                if (manager != null) {
                    val bundle = Bundle()
                    bundle.putParcelable(DetailsFragment.BUNDLE_EXTRA, weather)
                    manager.beginTransaction()
                        .add(R.id.container, DetailsFragment.newInstance(bundle))
                        .addToBackStack("")
                        .commitAllowingStateLoss()
                }
            }
        })

        binding.mainFragmentRecyclerView.adapter = adapter
        binding.mainFragmentFAB.setOnClickListener {
            changeWeatherDataSet()
        }

        val observer = Observer<AppState> { a -> renderData(a) }
        viewModel.getData().observe(viewLifecycleOwner, observer)
        viewModel.getWheatherFromLocalStorageRus()
    }

    private fun changeWeatherDataSet() {
        if (isDataSetRus) {
            viewModel.getWheatherFromLocalStorageRus()
            binding.mainFragmentFAB.setImageResource(R.drawable.ic_russia)
        } else {
            viewModel.getWheatherFromLocalStorageWorld()
            binding.mainFragmentFAB.setImageResource(R.drawable.ic_earth)
        }
        isDataSetRus = !isDataSetRus
    }

    private fun renderData(data: AppState) {
        when (data) {
            is AppState.Success -> {
                val weatherData = data.weatherData
                binding.mainFragmentLoadingLayout.visibility = View.GONE
                adapter.setWeather(weatherData)
            }

            is AppState.Loading -> {
                binding.mainFragmentLoadingLayout.visibility = View.VISIBLE
            }

            is AppState.Error -> {
                binding.mainFragmentLoadingLayout.visibility = View.GONE
                Snackbar.make(binding.mainFragmentFAB, "Error", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Reload") {
                        if (isDataSetRus) viewModel.getWheatherFromLocalStorageRus()
                        else viewModel.getWheatherFromLocalStorageWorld()
                    }
                    .show()
            }

        }

    }

    interface OnItemViewClickListener {
        fun onItemViewClick(weather: Weather)
    }
}