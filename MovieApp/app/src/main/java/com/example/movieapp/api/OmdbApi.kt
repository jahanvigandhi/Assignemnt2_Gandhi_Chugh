package com.example.movieapp.api

import com.example.movieapp.model.Movie
import com.example.movieapp.model.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface OmdbApi {
    @GET("/")
    suspend fun searchMovies(
        @Query("s") search: String,
        @Query("apikey") apiKey: String
    ): SearchResponse

    @GET("/")
    suspend fun getMovieDetails(
        @Query("i") imdbId: String,
        @Query("apikey") apiKey: String
    ): Movie
}