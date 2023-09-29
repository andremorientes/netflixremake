package co.tiagoaguiar.netflixremake

import android.content.Intent
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

    private var categories= mutableListOf<Category>()
    private lateinit var progress: ProgressBar
    private lateinit var adapter : CategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // ARQUITETURA MVC (MODEL-VIEW- CONTROLLER)

        progress= findViewById(R.id.progress_bar)


         adapter= CategoryAdapter(categories){id->
             val intent = Intent(this@MainActivity, MovieActivity::class.java)
             intent.putExtra("id",id)
             startActivity(intent)

         }
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
        this.categories.clear()
        this.categories.addAll(categories)
        adapter.notifyDataSetChanged()
        progress.visibility= View.GONE
    }

    override fun onFailure(message: String) {
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show()
        progress.visibility= View.GONE
    }



}