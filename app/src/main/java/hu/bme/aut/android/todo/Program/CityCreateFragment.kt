package hu.bme.aut.android.todo.Program

import android.content.Context
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import hu.bme.aut.android.todo.R
import hu.bme.aut.android.todo.model.City
import kotlinx.android.synthetic.main.fragment_create.*

class CityCreateFragment : DialogFragment() {

    private lateinit var listener: CityCreatedListener

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        try {
            listener = if (targetFragment != null) {
                targetFragment as CityCreatedListener
            } else {
                activity as CityCreatedListener
            }
        } catch (e: ClassCastException) {
            throw RuntimeException(e)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_create, container, false)
        dialog.setTitle(getString(R.string.add_city))
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


       btnCreateCity.setOnClickListener {
           /*  val selectedPriority = when (spnrCityPriority.selectedItemPosition) {
                0 -> City.Priority.LOW
                1 -> City.Priority.MEDIUM
                2 -> City.Priority.HIGH
                else -> City.Priority.LOW
            }*/

           if (testText(etCityTitle)){
               if(testText(etCityDescription)){
                   if(testText(etSight1)){
                       if(testText(etSight2)){
                           if(testText(etSight3)){
                               listener.onCityCreated(City(
                                       Id = null,
                                       name = etCityTitle.text.toString(),
                                       description = etCityDescription.text.toString(),
                                       sight1 = etSight1.text.toString(),
                                       sight2 = etSight2.text.toString(),
                                       sight3 = etSight3.text.toString()
                               ))
                               dismiss()

                           }
                       }
                   }
               }
           }

       }


        btnCancelCreateCity.setOnClickListener{
            dismiss()
        }

    }

    fun testText(v: EditText):Boolean{
        if(v.text.isEmpty()){
            v.setError(getString(R.string.required))
            return false
        }
        return true
    }



    interface CityCreatedListener {
        fun onCityCreated(todo: City)
    }

}