package hu.bme.aut.android.todo.Program

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hu.bme.aut.android.todo.R
import hu.bme.aut.android.todo.model.City
import kotlinx.android.synthetic.main.city_detail.*
import hu.bme.aut.android.todo.Weather.WeatherDataHolder
import android.R.transition
import android.R.attr.description
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import hu.bme.aut.android.todo.Weather.Weather







/**
 * A fragment representing a single City detail screen.
 * This fragment is either contained in a [CityListActivity]
 * in two-pane mode (on tablets) or a [CityDetailActivity]
 * on handsets.
 */
class CityDetailFragment : Fragment() {


    private var selectedCity: City? = null

    companion object {
        const val KEY_DESC = "KEY_DESC"
        const val KEY_SIGHT1 = "KEY_SIGHT1"
        const val KEY_SIGHT3 = "KEY_SIGHT3"
        const val KEY_SIGHT2 = "KEY_SIGHT2"
        const val KEY_NAME = "KEY_NAME"
        private const val KEY_CITY_DESCRIPTION = "KEY_CITY_DESCRIPTION"
        private const val KEY_CITY_SIGHT1 = "KEY_CITY_SIGHT1"
        private const val KEY_CITY_SIGHT2 = "KEY_CITY_SIGHT2"
        private const val KEY_CITY_SIGHT3 = "KEY_CITY_SIGHT3"
        private const val KEY_CITY_NAME = "KEY_CITY_NAME"

        fun newInstance(cityName:String, CitySight1:String, CitySight2:String, CitySight3:String, cityDesc: String): CityDetailFragment {
            val args = Bundle()
            args.putString(KEY_CITY_NAME, cityName)
            args.putString(KEY_CITY_SIGHT1,  CitySight1)
            args.putString(KEY_CITY_SIGHT2,  CitySight2)
            args.putString(KEY_CITY_SIGHT3,  CitySight3)
            args.putString(KEY_CITY_DESCRIPTION, cityDesc)

            val result = CityDetailFragment()
            result.arguments = args
            return result
        }

    }

    private var weatherDataHolder: WeatherDataHolder? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (activity is WeatherDataHolder) {
            weatherDataHolder = activity as WeatherDataHolder?
        }

        arguments?.let { args ->
            selectedCity = City(
                    Id = null,
                    name =args.getString(KEY_CITY_NAME) ?:"PótBudapest",
                    sight1=args.getString(KEY_CITY_SIGHT1) ?:"PótHősök tere",
                    sight2=args.getString(KEY_CITY_SIGHT2) ?:"PótHalászBástya",
                    sight3=args.getString(KEY_CITY_SIGHT3) ?:"PótSzabadság szobor",
                    description = args.getString(KEY_CITY_DESCRIPTION) ?: "PÓTDescription"
            )
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.city_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvCityName.text=selectedCity?.name
        tvCityDetail.text = selectedCity?.description
        tvCitysight1.text=selectedCity?.sight1
        tvCitysight2.text=selectedCity?.sight2
        tvCitysight3.text=selectedCity?.sight3


    }

}
