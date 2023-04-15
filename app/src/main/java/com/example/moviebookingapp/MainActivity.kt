package com.example.moviebookingapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import okhttp3.*
import java.io.IOException
import kotlin.random.Random

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    private lateinit var movieAdapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val imageTop = findViewById<ImageView>(R.id.image_top)
        val randomImg = Random.nextInt(1000)
        val urlTwo = "https://picsum.photos/seed/$randomImg/2080/920?blur=10"
        Picasso.get().load(urlTwo).into(imageTop)


        val url = "https://gist.githubusercontent.com/MatheusPzz/a90bcb8b242ea5a74bba8dee8dd24c1a/raw/f971263b44230f9e555d24d84a3834ffa1adabd5/Movies.json"

        movieAdapter = MovieAdapter(emptyList()) { movie ->
            val intent = Intent(this, MovieFragActivity::class.java)
            intent.putExtra("movie", movie)
            startActivityForResult(intent, MOVIE_ACTIVITY_REQUEST_CODE)
        }

        val recyclerView: RecyclerView = findViewById(R.id.recyclerList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = movieAdapter

        url.makeHTTPRequest()
    }

    private fun String.makeHTTPRequest(){
        val okClient = OkHttpClient()
        val okRequest = Request.Builder()
            .url(this)
            .build()
        okClient.newCall(okRequest).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException){
                Log.e("MainActivity", "HTTP Request Failed")
            }

            override fun onResponse(call: Call, response: Response){
                if(response.isSuccessful){
                    val body = response.body?.string()
                    val gson = Gson()
                    val movieD: Array<MovieData> = gson.fromJson(body, Array<MovieData>::class.java)

                    movieD.forEach { movie ->
                        movie.seatsRemaining = Random.nextInt(0,16)
                    }

                    runOnUiThread {
                        movieAdapter.moviesUpdate(movieD.toList())

                    }
                }
            }
        })
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if(requestCode == MOVIE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val updMovie = data?.getSerializableExtra("updMovie")as? MovieData
            updMovie?.let {
                movieAdapter.updOtherMovies(it)
            }
        }
    }

    companion object {
        const val MOVIE_ACTIVITY_REQUEST_CODE = 1001
    }


}