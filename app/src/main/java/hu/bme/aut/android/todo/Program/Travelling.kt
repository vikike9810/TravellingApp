package hu.bme.aut.android.todo.Program

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import hu.bme.aut.android.todo.R
import kotlinx.android.synthetic.main.activity_city_list.*

class Travelling : AppCompatActivity(){

    var city: String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme_NoActionBar)
        setContentView(R.layout.travelling)

        setSupportActionBar(toolbar)
        toolbar.title = title

        city=intent.getStringExtra("KEY_CITYNAMETOTRAVELL")

    }

    fun carClick(v: View){
        val gmmIntentUri = Uri.parse("google.navigation:q="+city+"+city&mode=d")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        startActivity(mapIntent)
    }

    fun airplainClick(v:View) {
        val webpage: Uri = Uri.parse("https://www.google.com/flights")
        val intent = Intent(Intent.ACTION_VIEW, webpage)
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }

    }

    fun PublicClick(v: View){
        val gmmIntentUri = Uri.parse("google.navigation:q="+city+"+city&mode=r")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        startActivity(mapIntent)
    }
}