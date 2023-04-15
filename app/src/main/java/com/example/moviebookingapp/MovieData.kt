package com.example.moviebookingapp

data class MovieData(

    val id: Int,
    val fullTitle: String,
    val image: String,
    val contentRating: String,
    val stars: String,
    val plot: String,
    val runtimeStr: String,
    var seatsRemaining: Int,
    var seatsSelected: Int,

    ) : java.io.Serializable
