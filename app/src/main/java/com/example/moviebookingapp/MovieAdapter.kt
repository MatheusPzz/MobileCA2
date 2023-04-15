package com.example.moviebookingapp

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class MovieAdapter (private var movieL: List<MovieData>, private val onClickItem: (MovieData) -> Unit): RecyclerView.Adapter<RecyclerViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_items, parent,false)
        return RecyclerViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val movie = movieL[position]
        holder.bind(movie)
        holder.itemView.setOnClickListener {
            onClickItem(movie)
        }
    }

    override fun getItemCount(): Int = movieL.size

    @SuppressLint("NotifyDataSetChanged")
    fun moviesUpdate(otherMoviesUpd: List<MovieData>){
        movieL = otherMoviesUpd
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updOtherMovies(updMovie: MovieData){
        val position = movieL.indexOfFirst { it.id == updMovie.id }
        if (position != -1) {
            val otherMoviesUpd = movieL.toMutableList()
            otherMoviesUpd[position] = updMovie
            movieL = otherMoviesUpd
            notifyDataSetChanged()
        }
    }

}