package hu.bme.aut.android.todo.Weather.Network

import hu.bme.aut.android.todo.Weather.WeatherData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface WeatherApi {

    @GET("/data/2.5/weather")
    fun getWeather(
            @Query("q") cityName: String,
            @Query("units") units: String,
            @Query("appid") appId: String
    ): Call<WeatherData>

}