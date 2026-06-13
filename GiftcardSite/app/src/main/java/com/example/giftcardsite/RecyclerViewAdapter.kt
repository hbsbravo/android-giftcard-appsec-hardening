package com.example.giftcardsite

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.giftcardsite.api.model.Product
import com.example.giftcardsite.api.model.User
import de.hdodenhof.circleimageview.CircleImageView

class RecyclerViewAdapter(
    private val context: Context,
    private val productList: List<Product?>?,
    private val user: User?
) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun setData(product: Product?) {
            val image: CircleImageView = itemView.findViewById(R.id.image_view)
            val text: TextView = itemView.findViewById(R.id.name)

            if (product != null) {
                Glide.with(context)
                    .asBitmap()
                    .load("https://appsec.moyix.net/" + product.productImageLink)
                    .into(image)

                text.text = product.productName
            }

            image.setOnClickListener {
                if (product != null) {
                    val intent = Intent(context, GetCard::class.java).apply {
                        putExtra("User", user)
                        putExtra("Product", product)
                    }
                    Log.d("Intent", "About to intent.")
                    context.startActivity(intent)
                } else {
                    Log.d("Clicked something", "but it was null")
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.listitem, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = productList?.get(position)
        holder.setData(product)
    }

    override fun getItemCount(): Int {
        return productList?.size ?: 0
    }
}