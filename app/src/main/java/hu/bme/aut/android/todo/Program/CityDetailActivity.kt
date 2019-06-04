package hu.bme.aut.android.todo.Program

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v4.app.NavUtils
import android.view.MenuItem
import hu.bme.aut.android.todo.Program.CityDetailFragment.Companion.KEY_DESC
import hu.bme.aut.android.todo.Program.CityDetailFragment.Companion.KEY_NAME
import hu.bme.aut.android.todo.Program.CityDetailFragment.Companion.KEY_SIGHT1
import hu.bme.aut.android.todo.Program.CityDetailFragment.Companion.KEY_SIGHT2
import hu.bme.aut.android.todo.Program.CityDetailFragment.Companion.KEY_SIGHT3
import hu.bme.aut.android.todo.R
import kotlinx.android.synthetic.main.activity_city_detail.*
import hu.bme.aut.android.todo.Weather.WeatherData
import hu.bme.aut.android.todo.Weather.WeatherDataHolder
import android.widget.Toast
import android.support.annotation.NonNull
import android.support.v4.app.FragmentActivity
import android.util.Log

import hu.bme.aut.android.todo.Weather.Network.NetworkManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.support.v4.view.ViewPager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import kotlinx.android.synthetic.main.city_detail.*


/**
 * An activity representing a single City detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a [CityListActivity].
 */
class CityDetailActivity : AppCompatActivity(), WeatherDataHolder {

    var name:String?=null
    private var weatherData: WeatherData? = null

    override fun getWeatherData(): WeatherData? {
        return weatherData
    }

    private fun loadWeatherData() {
        NetworkManager.getWeather(name).enqueue(object : Callback<WeatherData> {
            override fun onResponse(call: Call<WeatherData>,
                                    response: Response<WeatherData>) {
                if (response.isSuccessful()) {
                    //Toast.makeText(this@CityDetailActivity,"Fasza",Toast.LENGTH_LONG).show()
                    displayWeatherData(response.body())}
                     else {
                    cantDisplayWeatherData()
                }
            }

            override fun onFailure(call: Call<WeatherData>, throwable: Throwable) {
                throwable.printStackTrace()
                cantDisplayWeatherData()
        }

    }
        )
    }

    private fun cantDisplayWeatherData(){
        tvWeatherMain.setText("Can not acces wheather infos")
    }

    private fun displayWeatherData(receivedWeatherData: WeatherData?) {
        weatherData = receivedWeatherData
        if (weatherData!=null) {
            tvWeatherMain.setText(weatherData?.main?.temp.toString() + " C°")
            tvWeatherDescription.setText(weatherData?.weather?.get(0)?.description)
            Glide.with(this)
                    .load("https://openweathermap.org/img/w/" + weatherData!!.weather!!.get(0)!!.icon + ".png")
                    .transition(DrawableTransitionOptions().crossFade())
                    .into(ivIcon)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city_detail)
        setSupportActionBar(detail_toolbar)

        fab.setOnClickListener {
            val intent = Intent(this, Travelling::class.java)
            intent.putExtra("KEY_CITYNAMETOTRAVELL",name)
            startActivity(intent)
        }

        // Show the Up button in the action bar.
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            name=intent.getStringExtra(KEY_NAME)
            val fragment = CityDetailFragment.newInstance(intent.getStringExtra(KEY_NAME), intent.getStringExtra(KEY_SIGHT1), intent.getStringExtra(KEY_SIGHT2), intent.getStringExtra(KEY_SIGHT3), intent.getStringExtra(KEY_DESC))

            supportFragmentManager.beginTransaction()
                    .add(R.id.city_detail_container, fragment)
                    .commit()
        }
        loadWeatherData()
    }


    override fun onOptionsItemSelected(item: MenuItem) =
            when (item.itemId) {
                android.R.id.home -> {
                    // This ID represents the Home or Up button. In the case of this
                    // activity, the Up button is shown. Use NavUtils to allow users
                    // to navigate up one level in the application structure. For
                    // more details, see the Navigation pattern on Android Design:
                    //
                    // http://developer.android.com/design/patterns/navigation.html#up-vs-back

                    NavUtils.navigateUpTo(this, Intent(this, CityListActivity::class.java))
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }
}
