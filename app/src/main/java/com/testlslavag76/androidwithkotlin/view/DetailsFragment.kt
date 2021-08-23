package com.testlslavag76.androidwithkotlin.view


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import com.testlslavag76.androidwithkotlin.R
import com.testlslavag76.androidwithkotlin.databinding.DetailsFragmentBinding
import com.testlslavag76.androidwithkotlin.model.AppState
import com.testlslavag76.androidwithkotlin.model.data.City
import com.testlslavag76.androidwithkotlin.model.data.Weather
import com.testlslavag76.androidwithkotlin.viewmodel.DetailsViewModel
import kotlinx.android.synthetic.main.details_fragment.*

private const val PROCESS_ERROR = "Обработка ошибки"
private const val MAIN_LINK = "https://api.weather.yandex.ru/v2/informers?"
private const val REQUEST_API_KEY = "X-Yandex-API-Key"

class DetailsFragment : Fragment() {

    private var _binding: DetailsFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var weatherBundle: Weather


    private val viewModel: DetailsViewModel by lazy { ViewModelProvider(this).get(DetailsViewModel::class.java) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.details_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        weatherBundle = arguments?.getParcelable(BUNDLE_EXTRA) ?: Weather()
        viewModel.detailsLiveData.observe(viewLifecycleOwner, Observer { renderData(it) })
        viewModel.getWeatherFromRemoteSource(weatherBundle.city.lat, weatherBundle.city.lon)
    }


    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
        binding.main.show()
        binding.loadingLayout.hide()
                setWeather(appState.weatherData[0])
            }
            is AppState.Loading -> {
                binding.main.hide()
                binding.loadingLayout.show()
            }
            is AppState.Error -> {
                binding.main.show()
                binding.loadingLayout.hide()
                binding.main.showSnacBar(
                    getString(R.string.error),
                    getString(R.string.reload),
                    { viewModel.getWeatherFromRemoteSource(weatherBundle.city.lat, weatherBundle.city.lon) })
            }
        }
    }

    private fun setWeather(weather: Weather) {
        val city = weatherBundle.city
        binding.cityName.text = city.city
        binding.cityCoordinates.text = String.format(
            getString(R.string.city_coordinates),
            city.lat.toString(),
            city.lon.toString()
        )
        saveCity(city, weather)

        binding.temperatureValue.text = weather.temperature.toString()
        binding.feelsLikeValue.text = weather.feelslike.toString()
        binding.weatherCondition.text = weather.condition

        Picasso
            .get()
            .load("https://freepngimg.com/thumb/city/36275-3-city-hd.png")
            .into(headerIcon);
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun saveCity(
        city: City,
        weather: Weather
    ) {
        viewModel.saveCityToDB(
            Weather(
                city,
                weather.temperature,
                weather.feelslike,
                weather.condition
            )
        )
    }



    companion object {

        const val BUNDLE_EXTRA = "weather"

        fun newInstance(bundle: Bundle): DetailsFragment {
            val fragment = DetailsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}

//private const val YOUR_API_KEY = "2154a906-4b17-4f82-9f3e-8476fa5ec70c"

//const val DETAILS_INTENT_FILTER = "DETAILS INTENT FILTER"
//const val DETAILS_LOAD_RESULT_EXTRA = "LOAD RESULT"
//const val DETAILS_INTENT_EMPTY_EXTRA = "INTENT IS EMPTY"
//const val DETAILS_DATA_EMPTY_EXTRA = "DATA IS EMPTY"
//const val DETAILS_RESPONSE_EMPTY_EXTRA = "RESPONSE IS EMPTY"
//const val DETAILS_REQUEST_ERROR_EXTRA = "REQUEST ERROR"
//const val DETAILS_REQUEST_ERROR_MESSAGE_EXTRA = "REQUEST ERROR MESSAGE"
//const val DETAILS_URL_MALFORMED_EXTRA = "URL MALFORMED"
//const val DETAILS_RESPONSE_SUCCESS_EXTRA = "RESPONSE SUCCESS"
//const val DETAILS_TEMP_EXTRA = "TEMPERATURE"
//const val DETAILS_FEELS_LIKE_EXTRA = "FEELS LIKE"
//const val DETAILS_CONDITION_EXTRA = "CONDITION"
//private const val TEMP_INVALID = -100
//private const val FEELS_LIKE_INVALID = -100
//private const val PROCESS_ERROR = "Обработка ошибки"
//
//class DetailsFragment : Fragment() {
//
//    private var _binding: DetailsFragmentBinding? = null
//    private val binding get() = _binding!!
//    private lateinit var weatherBundle: Weather
//
//
//    private val loadResultsReceiver: BroadcastReceiver = object : BroadcastReceiver() {
//        override fun onReceive(context: Context, intent: Intent) {
//            when (intent.getStringExtra(DETAILS_LOAD_RESULT_EXTRA)) {
//                DETAILS_INTENT_EMPTY_EXTRA -> TODO(PROCESS_ERROR)
//                DETAILS_DATA_EMPTY_EXTRA -> TODO(PROCESS_ERROR)
//                DETAILS_RESPONSE_EMPTY_EXTRA -> TODO(PROCESS_ERROR)
//                DETAILS_REQUEST_ERROR_EXTRA -> TODO(PROCESS_ERROR)
//                DETAILS_REQUEST_ERROR_MESSAGE_EXTRA -> TODO(PROCESS_ERROR)
//                DETAILS_URL_MALFORMED_EXTRA -> TODO(PROCESS_ERROR)
//                DETAILS_RESPONSE_SUCCESS_EXTRA -> renderData(
//                    WeatherDTO(
//                        FactDTO(
//                            intent.getIntExtra(
//                                DETAILS_TEMP_EXTRA, TEMP_INVALID
//                            ),
//                            intent.getIntExtra(DETAILS_FEELS_LIKE_EXTRA, FEELS_LIKE_INVALID),
//                            intent.getStringExtra(
//                                DETAILS_CONDITION_EXTRA
//                            )
//                        )
//                    )
//                )
//                else -> TODO(PROCESS_ERROR)
//            }
//        }
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        context?.let {
//            LocalBroadcastManager.getInstance(it)
//                .registerReceiver(loadResultsReceiver, IntentFilter(DETAILS_INTENT_FILTER))
//        }
//    }
//
//    override fun onDestroy() {
//        context?.let {
//            LocalBroadcastManager.getInstance(it).unregisterReceiver(loadResultsReceiver)
//        }
//        super.onDestroy()
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        _binding = DetailsFragmentBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        weatherBundle = arguments?.getParcelable(BUNDLE_EXTRA) ?: Weather()
//        getWeather()
//    }
//
//    private fun getWeather() {
//        binding.main.show()
//        binding.loadingLayout.hide()
//        context?.let {
//            it.startService(Intent(it, DetailsService::class.java).apply {
//                putExtra(
//                    LATITUDE_EXTRA,
//                    weatherBundle.city.lat
//                )
//                putExtra(
//                    LONGITUDE_EXTRA,
//                    weatherBundle.city.lon
//                )
//            })
//        }
//    }
//
//    private fun renderData(weatherDTO: WeatherDTO) {
//        binding.main.show()
//        binding.loadingLayout.hide()
//
//        val fact = weatherDTO.fact
//        val temp = fact!!.temp
//        val feelsLike = fact.feels_like
//        val condition = fact.condition
//        if (temp == TEMP_INVALID || feelsLike == FEELS_LIKE_INVALID || condition == null) {
//            TODO(PROCESS_ERROR)
//        } else {
//            val city = weatherBundle.city
//            binding.cityName.text = city.city
//            binding.cityCoordinates.text = String.format(
//                getString(R.string.city_coordinates),
//                city.lat.toString(),
//                city.lon.toString()
//            )
//            binding.temperatureValue.text = temp.toString()
//            binding.feelsLikeValue.text = feelsLike.toString()
//            binding.weatherCondition.text = condition
//        }
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//
//    companion object {
//
//        const val BUNDLE_EXTRA = "weather"
//
//        fun newInstance(bundle: Bundle): DetailsFragment {
//            val fragment = DetailsFragment()
//            fragment.arguments = bundle
//            return fragment
//        }
//    }
//}
//
