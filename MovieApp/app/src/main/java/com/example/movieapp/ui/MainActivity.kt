package com.example.movieapp.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapp.databinding.ActivityMainBinding
import com.example.movieapp.model.Movie
import com.example.movieapp.viewmodel.MovieViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MovieViewModel
    private lateinit var adapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MovieViewModel::class.java)

        setupRecyclerView()
        setupObservers()

        binding.searchButton.setOnClickListener {
            val query = binding.searchEditText.text.toString().trim()
            if (query.isNotEmpty()) {
                viewModel.searchMovies(query)
            } else {
                Toast.makeText(this, "Please enter a search term", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupRecyclerView() {
        adapter = MovieAdapter { movie: Movie ->
            movie.imdbID?.let { imdbId ->
                val intent = Intent(this@MainActivity, MovieDetailsActivity::class.java)
                intent.putExtra("IMDB_ID", imdbId)
                startActivity(intent)
            }
        }
        binding.moviesRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }
    }

    private fun setupObservers() {
        viewModel.movies.observe(this) { movies ->
            adapter.setMovies(movies)
        }

        viewModel.error.observe(this) { error ->
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
        }
    }
}