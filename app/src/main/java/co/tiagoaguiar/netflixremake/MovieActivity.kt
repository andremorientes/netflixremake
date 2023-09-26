package co.tiagoaguiar.netflixremake

import android.graphics.drawable.LayerDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat

class MovieActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)

        val toolbar: Toolbar = findViewById(R.id.movie_toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //Busquei o desenhavel (Layer-List)
        val layerDrawable : LayerDrawable = ContextCompat.getDrawable(this,R.drawable.shadows) as LayerDrawable

        //Busquei o filme que eu quero
        val movieCover = ContextCompat.getDrawable(this, R.drawable.movie_4)

        //Atribui a esse layer-list o novo filme
        layerDrawable.setDrawableByLayerId(R.id.cover_drawable, movieCover)

        //Set no ImageView
        val coverImg: ImageView= findViewById(R.id.move_img)
        coverImg.setImageDrawable(layerDrawable)

    }
}