package com.example.app_superpedia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.app_superpedia.API.APIService
import com.example.app_superpedia.API.HeroDataResponse
import com.example.app_superpedia.HeroeDetail.Companion.EXTRA_ID
import com.example.app_superpedia.RecyclerView.HeroAdapter
import com.example.app_superpedia.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var retrofit: Retrofit

    private lateinit var adapter: HeroAdapter

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

            override fun onQueryTextChange(newText: String?) = false
        })

        adapter = HeroAdapter{ superheroId ->  navigateToDetail(superheroId) }
        binding.rvSuperhero.setHasFixedSize(true)
        binding.rvSuperhero.layoutManager = LinearLayoutManager(this)
        binding.rvSuperhero.adapter = adapter
    }

    private fun navigateToDetail(superheroId: String) {
        val intent = Intent(this, HeroeDetail::class.java)
        intent.putExtra(EXTRA_ID, superheroId)
        startActivity(intent)
    }

    private fun searchByName(query: String) {
            binding.progressBar.isVisible = true
            CoroutineScope(Dispatchers.IO).launch {
                val myResponse: Response<HeroDataResponse> =
                    retrofit.create(APIService::class.java).getSuperheroes(query)
                if (myResponse.isSuccessful) {
                    Log.i("alex", "funciona ")
                    val response: HeroDataResponse? = myResponse.body()
                    if (response != null) {
                        Log.i("alex", response.toString())
                        runOnUiThread {// como lo iniciaste en corutina no puedes modificar views asi que este metodo lo corre en el hilo principal
                            adapter.updateList(response.superheroes)
                            binding.progressBar.isVisible = false
                        }
                    }
                } else {
                    Log.i("alex", "No funciona ")
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

