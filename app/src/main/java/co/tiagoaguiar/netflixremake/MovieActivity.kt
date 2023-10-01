package co.tiagoaguiar.netflixremake

import android.graphics.drawable.LayerDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.tiagoaguiar.netflixremake.model.Movie
import co.tiagoaguiar.netflixremake.model.MovieDetail
import co.tiagoaguiar.netflixremake.util.MovieTask
import java.lang.IllegalStateException

class MovieActivity : AppCompatActivity(), MovieTask.Callback {

    private lateinit var txtTitle: TextView
    private lateinit var txtDesc: TextView
    private lateinit var txtCast: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)

        txtTitle = findViewById(R.id.movie_txt_title)
        txtDesc = findViewById(R.id.movie_txt_desc)
        txtCast = findViewById(R.id.movie_txt_cast)
        val rv: RecyclerView = findViewById(R.id.movie_rv_similar)

        val id= intent?.getIntExtra("id",0)?: throw IllegalStateException("ID não foi encontrado ")
        val url= "https://api.tiagoaguiar.co/netflixapp/movie/$id?apiKey=e6012364-f841-498c-85ed-9be1b16b46d6"

        MovieTask(this).execute(url)

        txtTitle.text = "Batman v Super-Homem: O Despertar da Justiça"
        txtDesc.text = "O confronto entre Superman e Zod em Metrópolis fez a população mundial se" +
                " dividir sobre a presença de extraterrestres na Terra. Enquanto muitos consideram" +
                " Superman um novo deus, " +
                "há aqueles que entendem ser extremamente perigosa a existência de um ser tão "

        txtCast.text = getString(
            R.string.cast, "Ben Affleck como Bruce Wayne / Batman\n" +
                    "Henry Cavill como Clark Kent / Superman\n" +
                    "Amy Adams como Lois Lane\n" +
                    "Jesse Eisenberg como Lex Luthor"
        )


        val movies = mutableListOf<Movie>()




        rv.layoutManager = GridLayoutManager(this, 3)
        rv.adapter = MovieAdapter(movies, R.layout.movie_item_similar)


        val toolbar: Toolbar = findViewById(R.id.movie_toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = null

        //Busquei o desenhavel (Layer-List)
        val layerDrawable: LayerDrawable =
            ContextCompat.getDrawable(this, R.drawable.shadows) as LayerDrawable

        //Busquei o filme que eu quero
        val movieCover = ContextCompat.getDrawable(this, R.drawable.movie_4)

        //Atribui a esse layer-list o novo filme
        layerDrawable.setDrawableByLayerId(R.id.cover_drawable, movieCover)

        //Set no ImageView
        val coverImg: ImageView = findViewById(R.id.move_img)
        coverImg.setImageDrawable(layerDrawable)


    }

    override fun onPreExecute() {

    }

    override fun onResult(moveDetail: MovieDetail) {

        Log.i("Teste", "Funcionando ${moveDetail.toString()}")
    }

    override fun onFailure(message: String) {

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}