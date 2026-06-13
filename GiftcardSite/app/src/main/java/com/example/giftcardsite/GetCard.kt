package com.example.giftcardsite

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.giftcardsite.api.model.BuyCardInfo
import com.example.giftcardsite.api.model.Card
import com.example.giftcardsite.api.model.Product
import com.example.giftcardsite.api.model.User
import com.example.giftcardsite.api.service.CardInterface
import de.hdodenhof.circleimageview.CircleImageView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Integer.parseInt

class GetCard : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_card)
        setSupportActionBar(findViewById(R.id.toolbar))

        val image: CircleImageView = findViewById(R.id.image_view)
        val product: Product? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("Product", Product::class.java)
        } else {
            intent.getParcelableExtra<Product>("Product")
        }

        findViewById<EditText>(R.id.amount).hint = product?.recommendedPrice.toString()

        Glide.with(this)
            .asBitmap()
            .load("https://appsec.moyix.net/" + product?.productImageLink)
            .into(image)

        val productNumber: Int? = product?.productId
        val loggedInUser: User? = intent.getParcelableExtra("User")
        val token: String = "Token " + loggedInUser?.token.toString()
        Log.d("Token check", token)

        val outerContext = this

        findViewById<Button>(R.id.submit_buy).setOnClickListener {
            val amount: Int = parseInt(findViewById<EditText>(R.id.amount).text.toString())

            val builder: Retrofit.Builder = Retrofit.Builder()
                .baseUrl("https://appsec.moyix.net")
                .addConverterFactory(GsonConverterFactory.create())
            val retrofit: Retrofit = builder.build()
            val client: CardInterface = retrofit.create(CardInterface::class.java)

            val buyCardInfo = BuyCardInfo(amount)
            Log.d("Buy Card Going", "Going to buy card now. Amount $amount")

            client.buyCard(productNumber, buyCardInfo, token)?.enqueue(object : Callback<Card?> {
                override fun onFailure(call: Call<Card?>, t: Throwable) {
                    Log.d("Buy Failure", "Buy Failure in onFailure")
                    Log.d("Buy Failure", t.message.toString())
                }

                override fun onResponse(call: Call<Card?>, response: Response<Card?>) {
                    if (!response.isSuccessful) {
                        Log.d("Buy Failure", "Buy failure. Yay.")
                    } else {
                        val newCard: Card? = response.body()
                        Log.d("Buy failure?", newCard.toString())
                        Log.d("Buy Success", "Card: $newCard")
                        Log.d("Buy Success", "Buy success. Boo.")
                        Log.d("Buy Success", "Token:$token")
                    }

                    val intent = Intent(outerContext, ProductScrollingActivity::class.java)
                    intent.putExtra("User", loggedInUser)
                    startActivity(intent)
                }
            })
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }
}