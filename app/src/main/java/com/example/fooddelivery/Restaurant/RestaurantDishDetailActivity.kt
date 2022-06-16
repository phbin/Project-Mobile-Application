package com.example.fooddelivery.Restaurant

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.fooddelivery.R
import com.example.fooddelivery.model.RestaurantDishesList
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_restaurant_dish_detail.*
import kotlinx.android.synthetic.main.activity_restaurant_dish_detail.btnBack
import java.io.ByteArrayOutputStream
import java.util.*

class RestaurantDishDetailActivity : AppCompatActivity() {
    var uploadImageSuccess : Boolean = false
    var storage= FirebaseStorage.getInstance()
    lateinit var downloadUri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_dish_detail)
        var key=""
        var storageRef: StorageReference =
            storage.getReferenceFromUrl("gs://food-delivering-7c7fc.appspot.com")
        var sharedPreferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        val phoneNumber = sharedPreferences.getString("ID", "")
        btnBack.setOnClickListener {
            finish()
        }
        val position = intent.getStringExtra("dishName")
        val categoryName = intent.getStringExtra("categoryName")

        var fb = FirebaseFirestore.getInstance().collection("Restaurant")
            .document("" + phoneNumber)
            .collection("categoryMenu")
            .document("" + categoryName)
            .collection("Item")
        fb.get().addOnCompleteListener {
            if (it.isSuccessful) {
                it.result.count()
                for ((index, i) in it.result.withIndex()) {
                    if (index.toString() == position) {
                        textName.setText(i.data.getValue("name").toString())
                        textPrice.setText(i.data.getValue("price").toString())
                        textSize.setText(i.data.getValue("size").toString())
                        //Toast.makeText(this,""+i.data.getValue("image"),Toast.LENGTH_LONG).show()
                        var url = "" + i.data.getValue("image")
                        Picasso.get()
                            .load(url)
                            .into(img)
                    }
                }
            }
        }
        img.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED
                ) {

                    val permission = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)

                    requestPermissions(permission, RestaurantDishDetailActivity.PERMISSION_CODE)
                } else {
                    pickImageFromGallery()
                }
            } else {
                pickImageFromGallery()
            }
        }

        btnContinue.setOnClickListener{
//            finish()
           if (textPrice.text.toString() != ""
                && textName.text.toString() != ""
                && img != null
            ) {
                progressBar.visibility= View.VISIBLE
                btnContinue.visibility=View.GONE

                var calendar = Calendar.getInstance()
                var mountainsRef: StorageReference =
                    storageRef.child("image" + calendar.timeInMillis + ".png")
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
                        uploadImageSuccess = true
                        downloadUri = task.result
                        Log.d("AAAA", "" + downloadUri)
                        ////////////////////load another data////////////////////
//                        var o = RestaurantDishesList(
//                            textName.text.toString(),
//                            textPrice.text.toString(),
//                            textSize.text.toString(),
//                            downloadUri.toString()
//                        )

                        var fb = FirebaseFirestore.getInstance().collection("Restaurant")
                            .document("" + phoneNumber)
                            .collection("categoryMenu")
                            .document("" + categoryName)
                            .collection("Item")
                        fb.get().addOnCompleteListener {
                            for((index,i) in it.result.withIndex()){
                                if(index.toString()==position){
                                    key=i.id
                                    if(downloadUri.toString()!=null){
                                        fb.document(""+key).update("image", downloadUri.toString())
                                        fb.document(""+key).update("name", textName.text.toString())
                                        fb.document(""+key).update("price", textPrice.text.toString())
                                        fb.document(""+key).update("size", textSize.text.toString())
                                    }else{
                                        fb.document(""+key).update("name", textName.text.toString())
                                        fb.document(""+key).update("price", textPrice.text.toString())
                                        fb.document(""+key).update("size", textSize.text.toString())
                                    }
                                }
                            }
                        }
                        fb.get().addOnCompleteListener {
                                val intent= Intent(this, RestaurantDishesManagementActivity::class.java)
                                intent.putExtra("menuName", categoryName)
                                startActivity(intent)
                        }
                    }
                }
            }else{
                if(textName.text.isEmpty()) {
                    textName.error = "Please enter Name"
                }
                if(textPrice.text.isEmpty()){
                    textPrice.error = "Please enter Price"
                }
//                if(!uploadImageSuccess){
//                    Toast.makeText(this, "Please upload picture", Toast.LENGTH_SHORT).show()
//                }
            }
        }
    }
    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, RestaurantDishDetailActivity.IMAGE_PICK_CODE)
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
            RestaurantDishDetailActivity.PERMISSION_CODE -> {
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
        if(resultCode == Activity.RESULT_OK && requestCode == RestaurantDishDetailActivity.IMAGE_PICK_CODE){
            img.setImageURI(data?.data)
            //imageUri= data?.data!!
        }
    }
}