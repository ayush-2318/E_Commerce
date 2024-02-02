package com.example.e_commerce

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ProductAdapter(val listOfProduct:List<Product>,private val context:Context):RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
    class ProductViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val productImg:ImageView=itemView.findViewById(R.id.iv_product)
        val productName:TextView=itemView.findViewById(R.id.tv_product_name)
        val productPrice:TextView=itemView.findViewById(R.id.tv_product_price)
        val productDes:TextView=itemView.findViewById(R.id.tv_product_des)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.layout_product,parent,false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val currentProduct=listOfProduct[position]
        holder.productName.text=currentProduct.name
        holder.productPrice.text=currentProduct.price
        holder.productDes.text=currentProduct.des
        // image loading by using glide
        Glide.with(context)
            .load(currentProduct.image)
            .into(holder.productImg)
    }
    override fun getItemCount(): Int {
        return listOfProduct.size
    }
}