package com.haris.home.repositories

import com.haris.home.data.dtos.ItemListDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface Api {

    @Headers(
        "X-RapidAPI-Key: a356eca938mshecd748d7e4377d7p18b321jsn5f7a486ec28a",
        "X-RapidAPI-Host: tasty.p.rapidapi.com"
    )
    @GET("recipes/list?from=0&size=20&tags=under_30_minutes")
    suspend fun getRecipes(): Response<ItemListDto>
}