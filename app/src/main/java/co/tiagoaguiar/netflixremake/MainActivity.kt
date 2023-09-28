package co.tiagoaguiar.netflixremake

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.tiagoaguiar.netflixremake.model.Category
import co.tiagoaguiar.netflixremake.model.Movie
import co.tiagoaguiar.netflixremake.util.CategoryTask

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // ARQUITETURA MVC (MODEL-VIEW- CONTROLLER)

        val categories= mutableListOf<Category>()


        // Na Vertical a lista CategoryAdpater de categorias
        // e dentro de cada item (TextView e Recycler view na Horizontal)
        //Cada categoria teremos uma lista de (MovieAdapter) de Filmes (imageView)
        val adapter= CategoryAdapter(categories)
        val rv: RecyclerView= findViewById(R.id.rv_main)
        rv.layoutManager=LinearLayoutManager(this)
        rv.adapter= adapter

        CategoryTask().execute("https://api.tiagoaguiar.co/netflixapp/home?apiKey=e6012364-f841-498c-85ed-9be1b16b46d6")

    }



}