package com.example.app_superpedia.API

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface APIService {
//suspend inicia una corrutina
    @GET("/api/241369668955648/search/{name}")
    suspend fun getSuperheroes(@Path("name") superheroName:String):Response<HeroDataResponse>

    //@GET("/api/241369668955648/{id}")
    //suspend fun getSuperheroDetail(@Path("id") superheroId:String):Response<HeroDetailResponse>
}