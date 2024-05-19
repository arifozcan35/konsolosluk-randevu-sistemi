package com.arifozcan.consulateapplication

import android.app.appsearch.StorageInfo
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.gesture.GestureStroke
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.arifozcan.consulateapplication.databinding.ActivityCountriesBinding
import com.arifozcan.consulateapplication.databinding.ActivityCountryOptionsBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage

class CountryOptions : AppCompatActivity() {

    private lateinit var binding : ActivityCountryOptionsBinding
    private lateinit var auth : FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var storage: FirebaseStorage

    // sharedPreferences tanımlama
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCountryOptionsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = Firebase.auth
        firestore = Firebase.firestore
        storage = Firebase.storage

        // SharedPreferences
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        // Seçilen ülke bilgisini al
        val selectedCountry = sharedPreferences.getString("selectedCountry", "")

        binding.txtCountryOption.text = selectedCountry


        /*
        // sayfa başlığı (ülke ismi)
        val selectedCountry = intent.getStringExtra("selectedCountry")
        val textView = findViewById<TextView>(R.id.txtCountryOption)
        textView.text = selectedCountry

         */


    }


    fun vizeTurleriClicked(view : View) {
        val intent = Intent(applicationContext, VizeTurleri::class.java)
        startActivity(intent)

    }

    fun randevuAlClicked(view : View) {
        val intent = Intent(applicationContext, RandevuAl::class.java)
        startActivity(intent)

    }

    fun vizeBasvurusuClicked(view : View) {
            val intent = Intent(applicationContext, BasvuruDurumu::class.java)
        startActivity(intent)

    }


    // option menü seçenekleri
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.options_menu2,menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.goback) {
            //Go back
            val intent = Intent(applicationContext, CountriesActivity::class.java)
            startActivity(intent)
            finish()

        }
        return super.onOptionsItemSelected(item)
    }

}

