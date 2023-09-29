package co.tiagoaguiar.netflixremake.util

import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import co.tiagoaguiar.netflixremake.model.Category
import co.tiagoaguiar.netflixremake.model.Movie
import org.json.JSONObject
import java.io.BufferedInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.nio.Buffer
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class CategoryTask(private  val callback: Callback) {

    private val handler = Handler(Looper.getMainLooper())
    interface Callback{

        fun onPreExecute()
        fun onResult(categories: List<Category>)
        fun onFailure(message: String)
    }

    fun execute(url: String) {
        callback.onPreExecute()

        //Nesse momento, estamos utilizando a UI-Thread(1)
        val executor = Executors.newSingleThreadExecutor()
        executor.execute {
            var urlConnection: HttpURLConnection?=null
            var buffer :BufferedInputStream?= null
            var stream: InputStream? = null

            try {

                // nesse momento estamos utilizado a NOVA_tread(processo paralelo)(2)
                val requestURL = URL(url) // Abrir uma url
                 urlConnection = requestURL.openConnection() as HttpURLConnection // abrir conexão
                urlConnection.readTimeout = 2000 // tempo de leitura 2s
                urlConnection.connectTimeout = 2000 // tempo de conexao 2s

                val statusCode = urlConnection.responseCode
                if (statusCode > 400) {
                    throw  IOException("Erro na comunicação com o servidor!")
                }

                stream = urlConnection.inputStream  // Sequencias de Byte
                //Forma 1 : simples e rapida de abrir uma conexao HTTP
                //val jsonAsString = stream.bufferedReader().use { it.readText() } // transformar de byte para String


                // Forma 2 : Leitura de Byte por Byte
                 buffer = BufferedInputStream(stream)
                val jsonAsString = toString(buffer)

                //O JSON esta preparado para ser convertido em uma DATA CLASS!!
                val categories = toCategories(jsonAsString)

                // Log.i("Teste", jsonAsString)

                handler.post {
                    callback.onResult(categories)
                }


            } catch (e: IOException) {
                val message= e.message ?: " erro desconhecido"
                Log.e("Teste",message , e)
                   callback.onFailure(message)

            } finally {
                urlConnection?.disconnect()
                stream?.close()
                buffer?.close()
            }


        }


    }
}

private fun toCategories(jsonAsString: String): List<Category> {
    val categories = mutableListOf<Category>()

    val jsonRoot = JSONObject(jsonAsString)
    val jsonCategories = jsonRoot.getJSONArray("category")
    for (i in 0 until jsonCategories.length()) {
        val jsonCategory = jsonCategories.getJSONObject(i)

        val title = jsonCategory.getString("title")
        // NA NOSSA API ESTAMOS A CARREGAR O TITULO DE CATEGORIA
        val jsonMovies = jsonCategory.getJSONArray("movie")

        val movies = mutableListOf<Movie>()

        for (j in 0 until jsonMovies.length()) {

            val jsonMovie = jsonMovies.getJSONObject(j)
            val id = jsonMovie.getInt("id")
            val coverUrl = jsonMovie.getString("cover_url")

            movies.add(Movie(id, coverUrl))

        }
        categories.add(Category(title, movies))

    }


    return categories
}

private fun toString(stream: InputStream): String {
    val bytes = ByteArray(1024)
    val byteArrayOutPutString = ByteArrayOutputStream()
    var read: Int
    while (true) {
        read = stream.read(bytes)
        if (read <= 0) {
            break
        }
        byteArrayOutPutString.write(bytes, 0, read)

    }
    return String(byteArrayOutPutString.toByteArray())

}