package co.tiagoaguiar.netflixremake

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.tiagoaguiar.netflixremake.model.Category
import co.tiagoaguiar.netflixremake.model.Movie
import co.tiagoaguiar.netflixremake.util.CategoryTask

class MainActivity : AppCompatActivity(), CategoryTask.Callback {

    private lateinit var progress: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // ARQUITETURA MVC (MODEL-VIEW- CONTROLLER)

        progress= findViewById(R.id.progress_bar)
        val categories= mutableListOf<Category>()


        // Na Vertical a lista CategoryAdpater de categorias
        // e dentro de cada item (TextView e Recycler view na Horizontal)
        //Cada categoria teremos uma lista de (MovieAdapter) de Filmes (imageView)
        val adapter= CategoryAdapter(categories)
        val rv: RecyclerView= findViewById(R.id.rv_main)
        rv.layoutManager=LinearLayoutManager(this)
        rv.adapter= adapter

        CategoryTask(this).execute("https://api.tiagoaguiar.co/netflixapp/home?apiKey=e6012364-f841-498c-85ed-9be1b16b46d6")

    }

    override fun onPreExecute() {
        progress.visibility= View.VISIBLE
    }

    override fun onResult(categories: List<Category>) {
        //Aqui sera quando o CategoryTask chamara de Volta (CALL BACK)
        progress.visibility= View.GONE
    }

    override fun onFailure(message: String) {
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show()
        progress.visibility= View.GONE
    }



}