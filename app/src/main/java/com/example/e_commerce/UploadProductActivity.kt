package com.example.e_commerce

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

class UploadProductActivity : AppCompatActivity() {
    private lateinit var productPreviewImg:ImageView
    private lateinit var productNameEdt:EditText
    private lateinit var productPriceEdt:EditText
    private lateinit var productDesEdt:EditText
    private lateinit var btnSelectProduct:Button
    private lateinit var btnUploadProduct:Button
    private lateinit var progressBar:ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_product)
        productPreviewImg=findViewById(R.id.iv_product_preview)
        productNameEdt=findViewById(R.id.et_product_name)
        productPriceEdt=findViewById(R.id.et_product_price)
        productDesEdt=findViewById(R.id.et_product_description)
        btnSelectProduct=findViewById(R.id.btn_select_product)
        btnUploadProduct=findViewById(R.id.btn_upload_product)
        progressBar=findViewById(R.id.progress_bar)
        btnSelectProduct.setOnClickListener {
            val galleryintent=Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galleryintent,101)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode== RESULT_OK&&requestCode==101){
            val uri=data?.data
            productPreviewImg.setImageURI(uri)
            btnUploadProduct.setOnClickListener {
                // upload the image
                // get the link of image
                progressBar.visibility= View.VISIBLE
                val productName=productNameEdt.text.toString()
                val productPrice=productPriceEdt.text.toString()
                val productDes=productDesEdt.text.toString()
                val fileName=UUID.randomUUID().toString()+".jpg"
                val storageref= FirebaseStorage.getInstance().reference.child("productImages/$fileName")
                storageref.putFile(uri!!).addOnSuccessListener {
                    val result=it.metadata?.reference?.downloadUrl
                    result?.addOnSuccessListener {
                       // progressBar.visibility=View.GONE
                        //Log.i("link123",it.toString())
                        uploadProduct(
                            productName,
                            productPrice,
                            productDes,
                            it.toString()
                        )
                    }
                }
            }
        }
    }
    private fun uploadProduct(name:String,price:String,des:String,link:String){
        Firebase.database.getReference("products").child(name).setValue(
           Product(
               image = link,
               name=name,
               price=price,
               des=des
           )
        ).addOnSuccessListener {
            progressBar.visibility=View.GONE
            Toast.makeText(this, "product upload successfully", Toast.LENGTH_SHORT).show()
        }
    }
}