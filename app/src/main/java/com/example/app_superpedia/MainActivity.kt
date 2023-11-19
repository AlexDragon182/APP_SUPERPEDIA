package com.example.app_superpedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import com.example.app_superpedia.API.APIService
import com.example.app_superpedia.API.HeroDataResponse
import com.example.app_superpedia.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var retrofit:Retrofit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        retrofit = getRetrofit()
        initUI()

    }
    private fun initUI() {
        binding.svSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchByName(query.orEmpty())
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun searchByName(query: String) {
        binding.progressBar.isVisible = true
        CoroutineScope(Dispatchers.IO).launch {
            val myResponse: Response<HeroDataResponse> = retrofit.create(APIService::class.java).getSuperheroes(query)
            if (myResponse.isSuccessful) {
                Log.i( "funciona :)")
                //val response: HeroDataResponse? = myResponse.body()
                //if (response != null) {
                    //Log.i("aristidevs", response.toString())
                    //runOnUiThread {
                        //adapter.updateList(response.superheroes)
                        //binding.progressBar.isVisible = false
                    //}
                //}
            } else {
                Log.i("No funciona ")
            }
        }
    }
    private fun getRetrofit(): Retrofit {
        //devuelve el objeto tipo retrofit que se esta creando , despues del retrofit
        return Retrofit
            .Builder()
            .baseUrl("https://superheroapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}