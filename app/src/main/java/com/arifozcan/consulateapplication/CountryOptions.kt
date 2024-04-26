package com.arifozcan.consulateapplication

import android.content.Intent
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

class CountryOptions : AppCompatActivity() {

    private lateinit var binding : ActivityCountryOptionsBinding
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCountryOptionsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = Firebase.auth


        // sayfa başlığı (ülke ismi)
        val selectedCountry = intent.getStringExtra("selectedCountry")
        val textView = findViewById<TextView>(R.id.txtCountryOption)
        textView.text = selectedCountry


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