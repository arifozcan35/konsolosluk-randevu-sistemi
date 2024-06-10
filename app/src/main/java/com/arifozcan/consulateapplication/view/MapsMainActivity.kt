package com.arifozcan.consulateapplication.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.arifozcan.consulateapplication.R
import com.arifozcan.consulateapplication.Visa
import com.arifozcan.consulateapplication.VisaAdapter
import com.arifozcan.consulateapplication.adapter.PlaceAdapter
import com.arifozcan.consulateapplication.databinding.ActivityBasvuruDurumuBinding
import com.arifozcan.consulateapplication.databinding.ActivityMapsMainBinding
import com.arifozcan.consulateapplication.model.Place
import com.arifozcan.consulateapplication.roomdb.PlaceDatabase
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MapsMainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMapsMainBinding
    private lateinit var auth : FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var storage: FirebaseStorage

    private lateinit var visaList : ArrayList<Visa>
    private lateinit var visaAdapter : VisaAdapter

    private val compositeDisposable = CompositeDisposable()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMapsMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = Firebase.auth
        firestore = Firebase.firestore
        storage = Firebase.storage


        val db = Room.databaseBuilder(applicationContext, PlaceDatabase::class.java, "Places").build()
        val placeDao = db.placeDao()

        compositeDisposable.add(
            placeDao.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse, this::handleError)
        )


    }

    private fun handleResponse(placeList: List<Place>) {
        binding.recyclerViewMap.layoutManager = LinearLayoutManager(this)
        val placeAdapter = PlaceAdapter(placeList)
        binding.recyclerViewMap.adapter = placeAdapter
    }

    private fun handleError(error: Throwable) {
        // Hata loglama
        error.printStackTrace()

        // Kullanıcıya hata mesajı gösterme
        Toast.makeText(this, "Bir hata oluştu: ${error.localizedMessage}", Toast.LENGTH_LONG).show()
    }





    // option menü seçenekleri
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.options_map,menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.map) {
            // harita açma
            val intent = Intent(applicationContext, MapsActivity::class.java)
            intent.putExtra("info", "new")
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}