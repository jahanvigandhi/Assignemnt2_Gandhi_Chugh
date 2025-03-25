package com.example.movieapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.databinding.MovieItemBinding
import com.example.movieapp.model.Movie

class MovieAdapter(private val onClick: (Movie) -> Unit) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {
    private var movies: List<Movie> = emptyList()

    fun setMovies(newMovies: List<Movie>) {
        movies = newMovies
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    override fun getItemCount(): Int = movies.size

    inner class MovieViewHolder(private val binding: MovieItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            binding.titleTextView.text = movie.Title ?: "Unknown Title"
            binding.studioTextView.text = "Studio: ${movie.Production ?: "Unknown Studio"}"
            binding.ratingTextView.text = "Rating: ${movie.imdbRating ?: "Not Rated"}"
            binding.yearTextView.text = "Year: ${movie.Year ?: "Unknown Year"}"

            // Load the poster image using Glide
            movie.Poster?.let { posterUrl ->
                if (posterUrl != "N/A") {
                    Glide.with(binding.root.context)
                        .load(posterUrl)
                        .into(binding.posterImageView)
                } else {
                    binding.posterImageView.setImageResource(android.R.drawable.ic_menu_gallery) // Fallback image
                }
            } ?: binding.posterImageView.setImageResource(android.R.drawable.ic_menu_gallery) // Fallback image if Poster is null

            binding.root.setOnClickListener {
                onClick(movie)
            }
        }
    }
}