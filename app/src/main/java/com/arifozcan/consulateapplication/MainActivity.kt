package com.arifozcan.consulateapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.arifozcan.consulateapplication.databinding.ActivityMainBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = Firebase.auth


        val currentUser = auth.currentUser

        if(currentUser != null){
            val intent = Intent(this, CountriesActivity::class.java)
            startActivity(intent)
            finish()
        }


    }


    fun signInClicked(view : View) {
        val email = binding.txtEmail.text.toString()
        val password = binding.txtPassword.text.toString()

        if(email.equals("") || password.equals("")){
            Toast.makeText(this, "Enter mail and password!", Toast.LENGTH_LONG).show()
        }
        else{
            auth.signInWithEmailAndPassword(email,password).addOnSuccessListener {
                //success
                val intent = Intent(this, CountriesActivity::class.java)
                startActivity(intent)
                finish()
            }.addOnFailureListener {
                Toast.makeText(this, it.localizedMessage, Toast.LENGTH_LONG).show()
            }
        }


    }


    fun signUpClicked(view: View) {
        val email = binding.txtEmail.text.toString()
        val password = binding.txtPassword.text.toString()

        if(email.equals("") || password.equals("")){
            Toast.makeText(this, "Enter mail and password!", Toast.LENGTH_LONG).show()
        }
        else{
            auth.createUserWithEmailAndPassword(email,password).addOnSuccessListener {
                //success
                val intent = Intent(this, CountriesActivity::class.java)
                startActivity(intent)
                finish()
            }.addOnFailureListener {
                Toast.makeText(this, it.localizedMessage, Toast.LENGTH_LONG).show()
            }
        }
    }


}