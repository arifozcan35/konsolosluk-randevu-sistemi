package com.arifozcan.consulateapplication

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.arifozcan.consulateapplication.databinding.ActivityCountriesBinding
import com.arifozcan.consulateapplication.databinding.ActivityMainBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class CountriesActivity : AppCompatActivity() {

    private lateinit var binding : ActivityCountriesBinding
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCountriesBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = Firebase.auth






        // Spinnerde ülke isimlerini gösterme ve seçme
        var countriesName = resources.getStringArray(R.array.CountriesName)

        if(binding.spinnerCountries != null){
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, countriesName)
            binding.spinnerCountries.adapter = adapter

            binding.spinnerCountries.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    Toast.makeText(applicationContext, "Seçilen Ülke : " + countriesName[position],
                        Toast.LENGTH_SHORT).show()
                    binding.txtCountry.text = countriesName[position]
                    val intent = Intent(applicationContext, CountryOptions::class.java)
                    startActivity(intent)
                    finish()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }
            }
        }



        /*
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

         */
    }









    // option menü seçenekleri
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.options_menu,menu)

        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.contact) {
            //Upload Activity
            val intent = Intent(applicationContext, CountriesActivity::class.java)
            startActivity(intent)

        } else if (item.itemId == R.id.signout) {
            //Logout

            auth.signOut()
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
            finish()

        }

        return super.onOptionsItemSelected(item)
    }

}