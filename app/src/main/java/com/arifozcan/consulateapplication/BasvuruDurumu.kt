package com.arifozcan.consulateapplication

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.arifozcan.consulateapplication.databinding.ActivityBasvuruDurumuBinding
import com.arifozcan.consulateapplication.databinding.ActivityRandevuAlBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage

class BasvuruDurumu : AppCompatActivity() {
    private lateinit var binding : ActivityBasvuruDurumuBinding
    private lateinit var auth : FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var storage: FirebaseStorage

    private lateinit var visaList : ArrayList<Visa>
    private lateinit var visaAdapter : VisaAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityBasvuruDurumuBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = Firebase.auth
        firestore = Firebase.firestore
        storage = Firebase.storage


        visaList = ArrayList<Visa>()

        visaAdapter = VisaAdapter(visaList)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = visaAdapter



        // veri çekme
        try {
            val database = this.openOrCreateDatabase("Visas", MODE_PRIVATE, null)

            val cursor = database.rawQuery("SELECT * FROM visas", null)
            val customerNameIx = cursor.getColumnIndex("customerName")
            val idIx = cursor.getColumnIndex("id")

            while(cursor.moveToNext()){
                val name = cursor.getString(customerNameIx)
                val id = cursor.getInt(idIx)
                val visa = Visa(name, id)
                visaList.add(visa)
            }
            visaAdapter.notifyDataSetChanged()

            cursor.close()
        }
        catch (e: Exception){
            e.printStackTrace()
        }
    }


    // option menü seçenekleri
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.options_vize,menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.applyvisa) {
            //Go back
            val intent = Intent(applicationContext, VizeBasvurusu::class.java)
            intent.putExtra("info", "new")
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}