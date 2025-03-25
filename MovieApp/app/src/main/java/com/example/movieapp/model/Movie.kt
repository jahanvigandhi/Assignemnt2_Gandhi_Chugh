package com.example.movieapp.model

data class Movie(
    val Title: String?,
    val Year: String?,
    val Rated: String?,
    val Released: String?,
    val Runtime: String?,
    val Genre: String?,
    val Director: String?,
    val Writer: String?,
    val Actors: String?,
    val Plot: String?,
    val Language: String?,
    val Country: String?,
    val Poster: String?,
    val imdbRating: String?,
    val imdbID: String?,
    val Type: String?,
    val Production: String?
)

data class SearchResponse(
    val Search: List<Movie>?,
    val totalResults: String?,
    val Response: String?
)