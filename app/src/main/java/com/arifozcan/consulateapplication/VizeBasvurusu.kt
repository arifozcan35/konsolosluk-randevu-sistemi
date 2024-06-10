package com.arifozcan.consulateapplication

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.arifozcan.consulateapplication.databinding.ActivityVizeBasvurusuBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage
import java.io.ByteArrayOutputStream

class VizeBasvurusu : AppCompatActivity() {

    private lateinit var binding : ActivityVizeBasvurusuBinding


    private lateinit var auth : FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var storage: FirebaseStorage

    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var permissionLauncher: ActivityResultLauncher<String>

    var selectedBitmap : Bitmap? = null
    private lateinit var database: SQLiteDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityVizeBasvurusuBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = Firebase.auth
        firestore = Firebase.firestore
        storage = Firebase.storage

        database = this.openOrCreateDatabase("Visas", MODE_PRIVATE, null)


        registerLauncher()

        val intent = intent
        val info = intent.getStringExtra("info")
        if(info.equals("new")){
            binding.txtNameSurname.setText("")
            binding.txtDateBirth.setText("")
            binding.txtPhone.setText("")
            binding.btnSave.visibility = View.VISIBLE
            binding.imgSelect.setImageResource(R.drawable.selectimage)
        }else{
            binding.btnSave.visibility = View.INVISIBLE
            val selectedId = intent.getIntExtra("id", 1)

            val cursor = database.rawQuery("SELECT * FROM visas WHERE id = ?", arrayOf(selectedId.toString()))

            val visaCustomerNameIx = cursor.getColumnIndex("customerName")
            val visaCustomerDateIx = cursor.getColumnIndex("customerDate")
            val visaCustomerPhoneIx = cursor.getColumnIndex("customerPhone")
            val imageIx = cursor.getColumnIndex("image")

            while(cursor.moveToNext()){
                binding.txtNameSurname.setText(cursor.getString(visaCustomerNameIx))
                binding.txtDateBirth.setText(cursor.getString(visaCustomerDateIx))
                binding.txtPhone.setText(cursor.getString(visaCustomerPhoneIx))

                val byteArray = cursor.getBlob(imageIx)
                val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
                binding.imgSelect.setImageBitmap(bitmap)
            }

            cursor.close()
        }

    }

    fun imgSelectClicked(view: View){
        // Galeriden resim yüklemek için izinleri almak

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            // Android 33+ ise ...
            // READ_MEDIA_IMAGES izni isteyeceğiz
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED){

                if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_MEDIA_IMAGES)){
                    // rationale
                    Snackbar.make(view, "Permission needed for gallery", Snackbar.LENGTH_INDEFINITE).setAction("Give Permission", View.OnClickListener {
                        // request permission
                        permissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
                    }).show()
                }else{
                    // request permission
                    permissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
                }

            }else {
                // intent
                val intentToGallery =   Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                activityResultLauncher.launch(intentToGallery)
            }
        }else{
            // 32- ise
            // READ_EXTERNAL_STORAGE
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

                if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)){
                    // rationale
                    Snackbar.make(view, "Permission needed for gallery", Snackbar.LENGTH_INDEFINITE).setAction("Give Permission", View.OnClickListener {
                        // request permission
                        permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                    }).show()
                }else{
                    // request permission
                    permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                }

            }else {
                // intent
                val intentToGallery =   Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                activityResultLauncher.launch(intentToGallery)
            }
        }


    }

    fun btnSaveClicked(view: View){

        val customerName = binding.txtNameSurname.text.toString()
        val customerDate = binding.txtDateBirth.text.toString()
        val customerPhone = binding.txtPhone.text.toString()


        if (selectedBitmap != null){
            val smallBitmap = makeSmallerBitmap(selectedBitmap!!, 300)


            // görseli veriye çevirmek
            val outputStream = ByteArrayOutputStream()
            smallBitmap.compress(Bitmap.CompressFormat.PNG,50,outputStream)
            val byteArray = outputStream.toByteArray()

            // veritabanını oluşturmak (SQLite)
            try {
                // val database = this.openOrCreateDatabase("Visas", MODE_PRIVATE, null)
                database.execSQL("CREATE TABLE IF NOT EXISTS visas (id INTEGER PRIMARY KEY, customerName VARCHAR, customerDate VARCHAR, customerPhone VARCHAR, image BLOB)")

                val sqlString = "INSERT INTO visas (customerName, customerDate, customerPhone, image) VALUES (?, ?, ?, ?)"
                val statement = database.compileStatement(sqlString)
                statement.bindString(1, customerName)
                statement.bindString(2, customerDate)
                statement.bindString(3, customerPhone)
                statement.bindBlob(4, byteArray)

                statement.execute()
            }
            catch (e: Exception){
                e.printStackTrace()
            }

            val intent = Intent(this@VizeBasvurusu, BasvuruDurumu::class.java)
            // arkada ne kadar aktivite varsa hepsini kapatıyoruz sonra main kativiteye dönüyoruz
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
    }

    private fun makeSmallerBitmap(image: Bitmap, maximumSize: Int) : Bitmap {
        // seçtiğimiz resimlerin boyutunu küçültmek. sql'e küçültülmüş şekilde kaydedeceğiz
        var width = image.width
        var height = image.height

        val bitmapRatio : Double = width.toDouble() / height.toDouble()

        if(bitmapRatio > 1){
            // landscape
            width = maximumSize
            val scaledHeight = width / bitmapRatio
            height = scaledHeight.toInt()
        }else {
            // portrait
            height = maximumSize
            val scaleWidth = height * bitmapRatio
            width = scaleWidth.toInt()
        }

        return Bitmap.createScaledBitmap(image, width, height, true)
    }

    private fun registerLauncher(){
        activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
            if (result.resultCode == RESULT_OK) {
                val intentFromResult = result.data
                if (intentFromResult != null) {
                    val imageData = intentFromResult.data
                    try {
                        if (Build.VERSION.SDK_INT >= 28) {
                            val source = ImageDecoder.createSource(this@VizeBasvurusu.contentResolver, imageData!!)
                            selectedBitmap = ImageDecoder.decodeBitmap(source)
                            binding.imgSelect.setImageBitmap(selectedBitmap)
                        } else {
                            selectedBitmap = MediaStore.Images.Media.getBitmap(this@VizeBasvurusu.contentResolver, imageData)
                            binding.imgSelect.setImageBitmap(selectedBitmap)
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
        permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){ result ->
            if(result){
                // permission granted
                val intentToGallery =   Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                activityResultLauncher.launch(intentToGallery)
            }else {
                // permission denied
                Toast.makeText(this@VizeBasvurusu, "Permisson needed!", Toast.LENGTH_LONG).show()
            }
        }
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
            val intent = Intent(applicationContext, CountryOptions::class.java)
            startActivity(intent)
            finish()

        }
        return super.onOptionsItemSelected(item)
    }
}