package hu.bme.aut.android.todo.Weather.Network

import hu.bme.aut.android.todo.Weather.WeatherData
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit


class NetworkManager private constructor() {
    companion object {

        private val retrofit: Retrofit
        private val weatherApi: WeatherApi


        private val SERVICE_URL = "https://api.openweathermap.org"
        private val APP_ID = "1451e68e3df8d9a778a7e2d67940d901"


        init {
            retrofit = Retrofit.Builder()
                    .baseUrl(SERVICE_URL)
                    .client(OkHttpClient.Builder().build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            weatherApi = retrofit.create(WeatherApi::class.java)
        }

        fun getWeather(city: String?): Call<WeatherData> {
            var city2=city?: ""
            return weatherApi.getWeather(city2, "metric", APP_ID)
        }
    }

}