package co.tiagoaguiar.netflixremake

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.RecoverySystem
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.tiagoaguiar.netflixremake.model.Movie

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // ARQUITETURA MVC (MODEL-VIEW- CONTROLLER)

        val movies = mutableListOf<Movie>()
        for (i in 0 until 60){
            val movie= Movie("https://exemplo.com/$i")
            movies.add(movie)
        }
        val adapter= MainAdapter(movies)
        val rv: RecyclerView= findViewById(R.id.rv_main)
        rv.layoutManager=LinearLayoutManager(this)
        rv.adapter= adapter

    }



}