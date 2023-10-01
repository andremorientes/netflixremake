package co.tiagoaguiar.netflixremake

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.LayerDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.tiagoaguiar.netflixremake.model.Movie
import co.tiagoaguiar.netflixremake.model.MovieDetail
import co.tiagoaguiar.netflixremake.util.DownloadImageTask
import co.tiagoaguiar.netflixremake.util.MovieTask
import java.lang.IllegalStateException

class MovieActivity : AppCompatActivity(), MovieTask.Callback {

    private lateinit var txtTitle: TextView
    private lateinit var txtDesc: TextView
    private lateinit var txtCast: TextView
    private  lateinit var progress : ProgressBar
    private  lateinit var adapter: MovieAdapter
    private  var  movies = mutableListOf<Movie>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)

        txtTitle = findViewById(R.id.movie_txt_title)
        txtDesc = findViewById(R.id.movie_txt_desc)
        txtCast = findViewById(R.id.movie_txt_cast)
        progress= findViewById(R.id.progress)
        val rv: RecyclerView = findViewById(R.id.movie_rv_similar)

        val id= intent?.getIntExtra("id",0)?: throw IllegalStateException("ID não foi encontrado ")
        val url= "https://api.tiagoaguiar.co/netflixapp/movie/$id?apiKey=e6012364-f841-498c-85ed-9be1b16b46d6"

        MovieTask(this).execute(url)







        adapter= MovieAdapter(movies, R.layout.movie_item_similar)
        rv.layoutManager = GridLayoutManager(this, 3)
        rv.adapter = adapter


        val toolbar: Toolbar = findViewById(R.id.movie_toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = null




    }

    override fun onPreExecute() {
        progress.visibility= View.VISIBLE

    }

    override fun onResult(moveDetail: MovieDetail) {
        progress.visibility= View.GONE
        txtTitle.text =moveDetail.movie.title
        txtDesc.text = moveDetail.movie.desc

        txtCast.text = getString(R.string.cast, moveDetail.movie.cast)

        movies.clear()
        movies.addAll(moveDetail.similars)
        adapter.notifyDataSetChanged()
        
        DownloadImageTask(object : DownloadImageTask.Callback {
            override fun onResult(bitmap: Bitmap) {
                //Busquei o desenhavel (Layer-List)
                val layerDrawable: LayerDrawable =
                    ContextCompat.getDrawable(this@MovieActivity, R.drawable.shadows) as LayerDrawable

                //Busquei o filme que eu quero
                val movieCover = BitmapDrawable(resources, bitmap)
                //Atribui a esse layer-list o novo filme
                layerDrawable.setDrawableByLayerId(R.id.cover_drawable, movieCover)
                //Set no ImageView
                val coverImg: ImageView = findViewById(R.id.move_img)
                coverImg.setImageDrawable(layerDrawable)
            }

        }).execute(moveDetail.movie.coverUrl)

    }

    override fun onFailure(message: String) {
        progress.visibility= View.GONE

        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}