package hu.bme.aut.android.todo.Program

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.design.widget.Snackbar
import android.view.View
import hu.bme.aut.android.todo.Dao.CityDatabase
import hu.bme.aut.android.todo.adapter.SimpleItemRecyclerViewAdapter
import hu.bme.aut.android.todo.model.City
import kotlinx.android.synthetic.main.activity_city_list.*
import kotlinx.android.synthetic.main.city_list.*
import hu.bme.aut.android.todo.R
import android.arch.persistence.room.Room
import android.content.Context
import android.database.Observable
import android.provider.SyncStateContract.Helpers.update
import android.os.AsyncTask
import android.support.v7.widget.PopupMenu
import hu.bme.aut.android.todo.Dao.CityDao
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem


/**
 * An activity representing a list of Pings. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a [CityDetailActivity] representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
class CityListActivity : AppCompatActivity(), CityCreateFragment.CityCreatedListener, SimpleItemRecyclerViewAdapter.CityItemClickListener {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private lateinit var simpleItemRecyclerViewAdapter: SimpleItemRecyclerViewAdapter
    private var twoPane: Boolean = false
    companion object {
       lateinit var  context :Context
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        try {
            Thread.sleep(1000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        setTheme(R.style.AppTheme_NoActionBar)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city_list)
        context=applicationContext


        setSupportActionBar(toolbar)
        toolbar.title = title

        fab.setOnClickListener { view ->
            val todoCreateFragment = CityCreateFragment()
            todoCreateFragment.show(supportFragmentManager, "TAG")
        }

      /*  if (city_detail_container != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            twoPane = true
        }*/

        setupRecyclerView()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.delete_all -> {
                deleteAll()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupRecyclerView() {
       /* val demoData = mutableListOf(
                City("Budapest","Hősök tere","HalászBástya","Szabadság szobor",  "Budapest[5] Magyarország fővárosa, egyben legnagyobb és legnépesebb városa, az Európai Unió tizedik legnépesebb városa. Budapest az ország politikai, kulturális, kereskedelmi, ipari és közlekedési központja. Emellett Pest megye székhelye is, de nem része annak. 2017-ben regisztrált lakónépessége meghaladta az 1,7 (elővárosokkal együtt pedig a 2,5) millió főt"),
                City("London", "London Eye","Big Ben","Westminster-palota ", "London az Egyesült Királyság és azon belül Anglia fővárosa, a legnagyobb városi terület az Egyesült Királyságban és az Európai Unióban, Európában Moszkva után a legnépesebb város, amely a Temze folyó két partján terül el. A története egészen az alapító rómaiakig nyúlik vissza, akik a várost Londiniumnak nevezték. A középkor emlékeit a város központjában található City of London őrzi, a mai London e köré épült ki."),
                City("Moszkva", "Kolomenszkoje","Kreml","Kuszkovo", "Moszkva (oroszul: Москва) Oroszország fővárosa. Az ország és egész Kelet-Közép-Európa elsődleges politikai, kulturális, gazdasági, pénzügyi, oktatási és közlekedési központja. A Moszkvai területen, a Moszkva folyó partján fekvő szövetségi jelentőségű város, a legfontosabb ortodox keresztény vallási központ, „a harmadik Róma”. Több mint 2500 km²-es területével és 12,5 milliós népességével Európa legnagyobb és legnépesebb városának számít. ")
        )*/

        val dbThread = Thread {
            val items = CityDatabase.getAppDataBase(this)!!.CityDao().getAll()
            runOnUiThread{
                simpleItemRecyclerViewAdapter = SimpleItemRecyclerViewAdapter()
                simpleItemRecyclerViewAdapter.itemClickListener = this
                simpleItemRecyclerViewAdapter.addAll(items)
                city_list.adapter = simpleItemRecyclerViewAdapter
            }
        }
        dbThread.start()

    }

    override fun onItemClick(city: City) {
        if (twoPane) {
            val fragment = CityDetailFragment.newInstance(city.name,city.sight1,city.sight2,city.sight3,city.description)
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.city_detail_container, fragment)
                    .commit()
        } else {
            val intent = Intent(this, CityDetailActivity::class.java)
            intent.putExtra(CityDetailFragment.KEY_DESC, city.description)
            intent.putExtra(CityDetailFragment.KEY_SIGHT1, city.sight1)
            intent.putExtra(CityDetailFragment.KEY_SIGHT2, city.sight2)
            intent.putExtra(CityDetailFragment.KEY_SIGHT3, city.sight3)
            intent.putExtra(CityDetailFragment.KEY_NAME, city.name)
            startActivity(intent)
        }
    }


    override fun onItemLongClick(position: Int, view: View): Boolean {
        val popup = PopupMenu(this, view)
        popup.inflate(R.menu.delete_menu)
        popup.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.delete -> CityDeleted(position)
            }
            false
        }
        popup.show()
        return false
    }

    fun CityDeleted(position: Int){
        val dbThread = Thread {
            CityDatabase.getAppDataBase(this@CityListActivity)!!.CityDao().deleteItem(simpleItemRecyclerViewAdapter.getItem(position))
            runOnUiThread {
                simpleItemRecyclerViewAdapter.deleteRow(position)
            }
        }
        dbThread.start()
    }

    fun deleteAll(){
        val dbThread = Thread {
            CityDatabase.getAppDataBase(this@CityListActivity)!!.CityDao().deleteAll()
            runOnUiThread {
                simpleItemRecyclerViewAdapter.deleteAll()
            }
        }
        dbThread.start()
    }


    override fun onCityCreated(city: City) {

        val dbThread = Thread {
            val id = CityDatabase.getAppDataBase(this@CityListActivity)!!.CityDao().insert(city)
            city.Id = id
            runOnUiThread{
                simpleItemRecyclerViewAdapter.addItem(city)
            }
        }
        dbThread.start()

    }

}
