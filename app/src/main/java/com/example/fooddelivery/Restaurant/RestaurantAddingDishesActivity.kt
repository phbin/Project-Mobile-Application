package com.example.fooddelivery.Restaurant

import android.Manifest
import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fooddelivery.R
import com.example.fooddelivery.model.RestaurantDishesList
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_restaurant_adding_dishes.*
import kotlinx.android.synthetic.main.activity_restaurant_adding_dishes.btnBack
import kotlinx.android.synthetic.main.activity_restaurant_adding_dishes.btnContinue
import kotlinx.android.synthetic.main.activity_restaurant_adding_dishes.editTextMenuName
import kotlinx.android.synthetic.main.activity_restaurant_adding_menu.*
import java.io.ByteArrayOutputStream
import java.net.URI
import java.text.SimpleDateFormat
import java.util.*

class RestaurantAddingDishesActivity: AppCompatActivity() {
    lateinit var downloadUri:Uri
    var storage=FirebaseStorage.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_adding_dishes)
        var sharedPreferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        val phoneNumber = sharedPreferences.getString("ID", "")
        val nameCategory=intent.getStringExtra("itemName")
        var storageRef: StorageReference =
            storage.getReferenceFromUrl("gs://food-delivering-7c7fc.appspot.com")
        btnBack.setOnClickListener {
            finish()
        }
        btnAddPicture.setOnClickListener {
            //check runtime permission
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED
                ) {

                    val permission = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)

                    requestPermissions(permission, PERMISSION_CODE)
                } else {
                    pickImageFromGallery()
                }
            } else {
                pickImageFromGallery()
            }
        }

        img.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED
                ) {

                    val permission = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)

                    requestPermissions(permission, PERMISSION_CODE)
                } else {
                    pickImageFromGallery()
                }
            } else {
                pickImageFromGallery()
            }
        }
        btnContinue.setOnClickListener {
            var calendar = Calendar.getInstance()
            var mountainsRef: StorageReference =
                storageRef.child("image" + calendar.timeInMillis + ".png")
            if (editTextSize.text.toString() != ""
                && editTextPrice.text.toString() != ""
                && editTextMenuName.text.toString() != ""
                && img != null
            ) {
                ////////////////////load image////////////////////
                // Get the data from an ImageView as bytes
                img.isDrawingCacheEnabled = true
                img.buildDrawingCache()
                val bitmap = (img.drawable as BitmapDrawable).bitmap
                val baos = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
                val data = baos.toByteArray()

                var uploadTask = mountainsRef.putBytes(data)
                uploadTask.addOnFailureListener {
                    // Handle unsuccessful uploads
                    Toast.makeText(this, "Fail!!", Toast.LENGTH_LONG).show()
                }.addOnSuccessListener { taskSnapshot ->
                    // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
                }
                val urlTask = uploadTask.continueWithTask { task ->
                    if (!task.isSuccessful) {
                        task.exception?.let {
                            throw it
                        }
                    }
                    mountainsRef.downloadUrl
                }.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        downloadUri = task.result
                        Log.d("AAAA", "" + downloadUri)
                        ////////////////////load another data////////////////////
                        var o=RestaurantDishesList(editTextMenuName.text.toString(),
                            editTextPrice.text.toString(),
                            editTextSize.text.toString(),
                            downloadUri.toString())
                        var fb = FirebaseFirestore.getInstance().collection("Restaurant")
                            .document(""+phoneNumber)
                            .collection("categoryMenu")
                            .document(""+nameCategory)
                            .collection("Item")
                        fb.add(o)
                            .addOnSuccessListener {
                                //val intent = Intent(this, RestaurantDishesManagementActivity::class.java)
                                finish()
                                //startActivity(intent)
                            }
                            .addOnFailureListener{
                                Toast.makeText(this,"Please try again!!", Toast.LENGTH_SHORT).show()
                            }
                    } else
                    {

                    }
                }
            }
        }
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    companion object {
        private val IMAGE_PICK_CODE = 1000

        private val PERMISSION_CODE = 1001
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            PERMISSION_CODE -> {
                if(grantResults.size > 0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED){
                    pickImageFromGallery()
                }
                else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE){
            img.setImageURI(data?.data)
            //imageUri= data?.data!!
            btnAddPicture.visibility = View.GONE
        }
    }
}