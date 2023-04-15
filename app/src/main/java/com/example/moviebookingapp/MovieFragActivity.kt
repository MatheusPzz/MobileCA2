package com.example.moviebookingapp

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isInvisible
import com.google.android.material.button.MaterialButton
import com.squareup.picasso.Picasso

@Suppress("DEPRECATION")
class MovieFragActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.movie_frag_activity)

        val movie: MovieData? = intent.getSerializableExtra("movie") as? MovieData

        if (movie != null) {

            setMovieData(movie)
        } else {

            Log.e("FragActivity", "The Object is returning null")
        }
    }


    @SuppressLint("SetTextI18n", "WrongViewCast", "ResourceType")
    private fun setMovieData(movie: MovieData) {

        val btnBack: MaterialButton = findViewById(R.id.button_back_img)
        val movieImg: ImageView = findViewById(R.id.movie_image)
        val certification: TextView = findViewById(R.id.movie_certif)
        val movieStars: TextView = findViewById(R.id.stars)
        val movieTitle: TextView = findViewById(R.id.movie_title)
        val movieDescription: TextView = findViewById(R.id.movie_desc)
        val movieRuntime: TextView = findViewById(R.id.runTime)
        val plusBtn: MaterialButton = findViewById(R.id.plus_button)
        val minBtn: MaterialButton = findViewById(R.id.minus_button)
        val seatsSelected: TextView = findViewById(R.id.selected_seats)
        val seatsRemaining: TextView = findViewById(R.id.seats_remaining)


        //This will load all the images into our image views

        Picasso.get()
            .load(movie.image)
            .into(movieImg)

        seatsSelected.text = movie.seatsSelected.toString()
        movieRuntime.text = movie.runtimeStr
        movieDescription.text = movie.plot
        movieTitle.text = movie.fullTitle
        movieStars.text = movie.stars
        certification.text = movie.contentRating


        // After setting all the values for the textViews we need to add the Picasso library in order to get the images

        // Now we get to the part which the program will check how many seats are there

        if (movie.seatsRemaining == 0) {
            seatsRemaining.text = getString(R.string.sold_out)
        } else {
            seatsRemaining.text = getString(R.string.seats_remaining, movie.seatsRemaining)
        }

        btnBack.setOnClickListener {
            val resIntent = Intent()
            resIntent.putExtra("updatedMovie", movie)
            setResult(Activity.RESULT_OK, resIntent)
            finish()
        }

        fun btnStates(){
            plusBtn.isEnabled = movie.seatsRemaining > 0
            minBtn.isEnabled = movie.seatsSelected > 0
            plusBtn.isInvisible = movie.seatsRemaining == 0
            minBtn.isInvisible = movie.seatsSelected == 0

        }

        btnStates()

        plusBtn.setOnClickListener {
            if(movie.seatsRemaining > 0){
                movie.seatsSelected++
                movie.seatsRemaining--
                seatsSelected.text = movie.seatsSelected.toString()
                seatsRemaining.text = getString(R.string.seats_remaining, movie.seatsRemaining)
                btnStates()
            }
        }
        minBtn.setOnClickListener {
            if(movie.seatsSelected > 0){
                movie.seatsSelected--
                movie.seatsRemaining++
                seatsSelected.text = movie.seatsSelected.toString()
                seatsRemaining.text = getString(R.string.seats_remaining, movie.seatsRemaining)
                btnStates()
            }
        }

    }

    @Deprecated("Deprecated in java")
    override fun onBackPressed() {
        val movie: MovieData? = intent.getSerializableExtra("movie") as? MovieData
        if (movie != null) {
            val resIntent = Intent()
            resIntent.putExtra("updMovie", movie)
            setResult(Activity.RESULT_OK, resIntent)
        }
        super.onBackPressed()
    }


}