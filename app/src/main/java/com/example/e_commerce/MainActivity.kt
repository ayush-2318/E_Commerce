package com.example.e_commerce

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var rv:RecyclerView
    private lateinit var fab:FloatingActionButton
    private lateinit var productAdapter:ProductAdapter
    val listOfProduct= mutableListOf<Product>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rv=findViewById(R.id.rv)
        fab=findViewById(R.id.fab)
        Firebase.database.getReference("products")
            .addValueEventListener(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    listOfProduct.clear()
                    for(datasnapshot in snapshot.children){
                        val product=datasnapshot.getValue(Product::class.java)
                        listOfProduct.add(product!!)
                        productAdapter= ProductAdapter(listOfProduct,this@MainActivity)
                        rv.adapter=productAdapter
                        rv.layoutManager=GridLayoutManager(this@MainActivity,2)

                    }
                }

                override fun onCancelled(error: DatabaseError) {
                   // TODO("Not yet implemented")
                }

            })


        fab.setOnClickListener {
            val intent=Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }
    }
}