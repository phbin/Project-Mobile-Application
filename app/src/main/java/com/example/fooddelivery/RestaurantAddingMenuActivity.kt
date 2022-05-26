package com.example.fooddelivery

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_restaurant_adding_menu.*
import android.Manifest
import android.app.Activity
import android.content.Intent
import android.view.View
import android.widget.Toast

class RestaurantAddingMenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_adding_menu)

        btnBack.setOnClickListener {
            finish()
        }

        btnAddPicture.setOnClickListener {
            //check runtime permission
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED){

                    val permission = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)

                    requestPermissions(permission, PERMISSION_CODE)
                }
                else {
                    pickImageFromGallery()
                }
            }
            else {
                pickImageFromGallery()
            }
        }

        imageView.setOnClickListener {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED){

                    val permission = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)

                    requestPermissions(permission, PERMISSION_CODE)
                }
                else {
                    pickImageFromGallery()
                }
            }
            else {
                pickImageFromGallery()
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
            imageView.setImageURI(data?.data)
            btnAddPicture.visibility = View.GONE
        }
    }
}