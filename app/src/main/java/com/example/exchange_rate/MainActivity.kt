package com.example.exchange_rate

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.exchange_rate.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var mainBinding: ActivityMainBinding
    var currentUsdValue: Int = 0
    var currentInrValue: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(mainBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        callApi()

        mainBinding.usdEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0.toString().isNotEmpty()) {
                    mainBinding.usdValueTv.text =
                        (p0.toString().toInt() * currentInrValue).toDouble().toString()
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
        mainBinding.inrEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0.toString().isNotEmpty()) {
                    mainBinding.inrValueTv.text =
                        (p0.toString().toInt() / currentInrValue).toDouble().toString()
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
    }


    private fun callApi() {
        RetrofitClient.exchangeService.getRate().enqueue(object : Callback<ExchangeModelResponse?> {
            override fun onResponse(
                call: Call<ExchangeModelResponse?>,
                response: Response<ExchangeModelResponse?>
            ) {
                Log.d("RESPONSE", response.body().toString())
                val currency = response.body()?.conversion_rates
                currentUsdValue = currency?.get("USD")!!.toInt()
                currentInrValue = currency?.get("INR")!!.toInt()

                mainBinding.usdCurrenyTv.text = currency.get("USD").toString()
                mainBinding.inrCurrenyTv.text = currency.get("INR").toString()
            }

            override fun onFailure(call: Call<ExchangeModelResponse?>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Something Went Wrong", Toast.LENGTH_SHORT).show()
            }
        })
    }
}