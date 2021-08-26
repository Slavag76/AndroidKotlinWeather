package com.testlslavag76.androidwithkotlin.view

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.testlslavag76.androidwithkotlin.R
import com.testlslavag76.androidwithkotlin.databinding.MainFragmentBinding
import com.testlslavag76.androidwithkotlin.model.AppState
import com.testlslavag76.androidwithkotlin.viewmodel.MainViewModel

private const val IS_RUSSIAN_KEY = "LIST_OF_TOWNS_KEY"


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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter.setOnItemViewClickListener { weather ->
            activity?.supportFragmentManager?.apply {
                beginTransaction()
                    .add(R.id.container, DetailsFragment.newInstance(Bundle().apply {

                        putParcelable(DetailsFragment.BUNDLE_EXTRA, weather)

                    }))
                    .addToBackStack("")
                    .commitAllowingStateLoss()
            }
        }

        binding.mainFragmentRecyclerView.adapter = adapter
        binding.mainFragmentFAB.setOnClickListener {
            changeWeatherDataSet()
            saveListOfTowns()

        }

        val observer = Observer<AppState> { a -> renderData(a) }
        viewModel.getData().observe(viewLifecycleOwner, observer)
         showListOfTowns()

    }

    private fun showListOfTowns() {
        activity?.let {
            if (it.getPreferences(Context.MODE_PRIVATE).getBoolean(IS_RUSSIAN_KEY, false)) {
                changeWeatherDataSet()
            } else {
                viewModel.getWheatherFromLocalStorageRus()
            }
        }
    }


    private fun saveListOfTowns() {
        activity?.let {
            with(it.getPreferences(Context.MODE_PRIVATE).edit()) {
                putBoolean(IS_RUSSIAN_KEY, isDataSetRus)
                apply()
            }
        }
    }


    private fun changeWeatherDataSet() {
        isDataSetRus = !isDataSetRus
        showWeatherDataSet()
    }

    private fun showWeatherDataSet() {
        if (isDataSetRus) {
            viewModel.getWheatherFromLocalStorageRus()
            binding.mainFragmentFAB.setImageResource(R.drawable.ic_russia)
        } else {
            viewModel.getWheatherFromLocalStorageWorld()
            binding.mainFragmentFAB.setImageResource(R.drawable.ic_earth)
        }
    }

    private fun renderData(data: AppState) {
        when (data) {
            is AppState.Success -> {

                binding.mainFragmentLoadingLayout.hide()
                adapter.setWeather(data.weatherData)
            }

            is AppState.Loading -> {
                binding.mainFragmentLoadingLayout.show()
            }

            is AppState.Error -> {
                binding.mainFragmentLoadingLayout.hide()
                binding.mainFragmentFAB.showSnacBar("Error", "Reload") {
                    if (isDataSetRus) viewModel.getWheatherFromLocalStorageRus()
                    else viewModel.getWheatherFromLocalStorageWorld()
                }
            }
        }
    }
}