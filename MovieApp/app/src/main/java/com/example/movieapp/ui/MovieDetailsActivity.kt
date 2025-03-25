package com.example.movieapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.databinding.ActivityMovieDetailsBinding
import com.example.movieapp.model.Movie
import com.example.movieapp.viewmodel.MovieViewModel

class MovieDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovieDetailsBinding
    private lateinit var viewModel: MovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MovieViewModel::class.java]

        val imdbId = intent.getStringExtra("IMDB_ID")
        imdbId?.let { viewModel.getMovieDetails(it) }

        binding.backButton.setOnClickListener {
            finish()
        }

        viewModel.movieDetails.observe(this) { movie: Movie ->
            binding.titleTextView.text = movie.Title ?: "N/A"

            val details = getString(
                R.string.movie_details_format,
                movie.Year ?: "N/A",
                movie.Rated ?: "N/A",
                movie.Released ?: "N/A",
                movie.Runtime ?: "N/A",
                movie.Genre ?: "N/A",
                movie.Director ?: "N/A",
                movie.Actors ?: "N/A",
                movie.imdbRating ?: "N/A",
                movie.Production ?: "N/A"
            )
            binding.detailsTextView.text = details
            binding.plotTextView.text = getString(R.string.movie_plot, movie.Plot ?: "N/A")

            movie.Poster?.let { posterUrl ->
                if (posterUrl != "N/A") {
                    Glide.with(this@MovieDetailsActivity)
                        .load(posterUrl)
                        .into(binding.posterImageView)
                }
            }
        }
    }
}