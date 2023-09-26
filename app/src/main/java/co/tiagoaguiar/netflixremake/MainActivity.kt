package co.tiagoaguiar.netflixremake

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.tiagoaguiar.netflixremake.model.Category
import co.tiagoaguiar.netflixremake.model.Movie

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // ARQUITETURA MVC (MODEL-VIEW- CONTROLLER)

        val categories= mutableListOf<Category>()
        for (j in 0 until 5){
            val movies = mutableListOf<Movie>()
            for (i in 0 until 8){
                val movie= Movie(R.drawable.movie)
                movies.add(movie)
            }
            val category= Category("Cat $j", movies)
            categories.add(category)
        }

        // Na Vertical a lista CategoryAdpater de categorias
        // e dentro de cada item (TextView e Recycler view na Horizontal)
        //Cada categoria teremos uma lista de (MovieAdapter) de Filmes (imageView)
        val adapter= CategoryAdapter(categories)
        val rv: RecyclerView= findViewById(R.id.rv_main)
        rv.layoutManager=LinearLayoutManager(this)
        rv.adapter= adapter

    }



}