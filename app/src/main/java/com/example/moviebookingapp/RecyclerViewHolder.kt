package com.example.moviebookingapp

import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class RecyclerViewHolder(itemView: View) :  RecyclerView.ViewHolder(itemView) {

    private val movieImg: ImageView = itemView.findViewById(R.id.movie_image)
    private val movieStars: TextView = itemView.findViewById(R.id.stars)
    private val runTime: TextView = itemView.findViewById(R.id.runTime)
    private val movieTitle: TextView = itemView.findViewById(R.id.movie_title)
    private val seatsRemaining: TextView = itemView.findViewById(R.id.seats_remaining)
    private val seatsIcon: ImageView = itemView.findViewById(R.id.movie_Seat_Icon)
    private val certification: TextView = itemView.findViewById(R.id.movie_certif)

    fun bind(movie: MovieData) {

        Picasso.get()
            .load(movie.image)
            .into(movieImg)

        movieTitle.text = movie.fullTitle
        certification.text = movie.contentRating
        movieStars.text = movie.stars
        runTime.text = movie.runtimeStr

        when {
            movie.seatsSelected > 0 -> {
                seatsRemaining.text =
                    itemView.context.getString(R.string.seats_selected, movie.seatsSelected)
                seatsRemaining.setTextColor(Color.GREEN)
                seatsIcon.setColorFilter(Color.GREEN)
            }
            movie.seatsRemaining == 0 -> {
                seatsRemaining.text = itemView.context.getString(R.string.sold_out)
                seatsRemaining.setTextColor(Color.parseColor("#d079db"))
                seatsIcon.clearColorFilter()

            }
            else -> {
                seatsRemaining.text = itemView.context.getString(R.string.seats_remaining, movie.seatsRemaining)
                seatsRemaining.setTextColor(Color.parseColor("#d079db"))
                seatsIcon.clearColorFilter()
            }
        }

        // Show or hide the filling fast badge depending on the number of seats remaining
        val fillingFastBadge: TextView = itemView.findViewById(R.id.movie_badge)
        if (movie.seatsRemaining in 1..2) {
            fillingFastBadge.visibility = View.VISIBLE
        } else {
            fillingFastBadge.visibility = View.GONE
        }

    }
}