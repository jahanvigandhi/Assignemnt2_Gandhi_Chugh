package com.example.movieapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.api.ApiClient
import com.example.movieapp.model.Movie
import com.example.movieapp.model.SearchResponse
import kotlinx.coroutines.launch
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll

class MovieViewModel : ViewModel() {
    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> = _movies

    private val _movieDetails = MutableLiveData<Movie>()
    val movieDetails: LiveData<Movie> = _movieDetails

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val apiKey = "d1f1809" // Your API key

    fun searchMovies(query: String) {
        viewModelScope.launch {
            try {
                // Step 1: Perform the initial search to get a list of movies
                val response: SearchResponse = ApiClient.omdbApi.searchMovies(query, apiKey)
                if (response.Response == "True") {
                    val searchResults = response.Search ?: emptyList()
                    // Step 2: Fetch full details for each movie using its imdbID
                    val detailedMovies = searchResults.map { movie ->
                        async {
                            movie.imdbID?.let { imdbId ->
                                ApiClient.omdbApi.getMovieDetails(imdbId, apiKey)
                            } ?: movie
                        }
                    }.awaitAll()
                    _movies.value = detailedMovies
                } else {
                    _error.value = "No movies found"
                }
            } catch (e: Exception) {
                _error.value = "Error: ${e.message}"
            }
        }
    }

    fun getMovieDetails(imdbId: String) {
        viewModelScope.launch {
            try {
                val movie: Movie = ApiClient.omdbApi.getMovieDetails(imdbId, apiKey)
                _movieDetails.value = movie
            } catch (e: Exception) {
                _error.value = "Error: ${e.message}"
            }
        }
    }
}