package com.arifozcan.consulateapplication

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.arifozcan.consulateapplication.databinding.ActivityCountriesBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class CountriesActivity : AppCompatActivity() {

    private lateinit var binding : ActivityCountriesBinding
    private lateinit var auth : FirebaseAuth


    // sharedPreferences tanımlama
    private lateinit var sharedPreferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCountriesBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        auth = Firebase.auth


        // SharedPreferences
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        

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
                    val selectedCountry = countriesName[position]
                    // Seçilen ülkeyi SharedPreferences'e kaydetme
                    sharedPreferences.edit().putString("selectedCountry", selectedCountry).apply()
                    Toast.makeText(applicationContext, "Seçilen Ülke : $selectedCountry", Toast.LENGTH_SHORT).show()
                    binding.txtCountry.text = selectedCountry


                    /*
                    Toast.makeText(applicationContext, "Seçilen Ülke : " + countriesName[position],
                        Toast.LENGTH_SHORT).show()
                    binding.txtCountry.text = countriesName[position]

                     */

                }
                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }
            }

        }

    }

    // ülke seç butonu
    public fun ulkeSecClicked(view : View) {
        val selectedCountry = binding.spinnerCountries.selectedItem.toString()
        val intentSayfa = Intent(applicationContext, CountryOptions::class.java)
        intentSayfa.putExtra("selectedCountry", selectedCountry)
        startActivity(intentSayfa)
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