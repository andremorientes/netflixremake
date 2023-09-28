package co.tiagoaguiar.netflixremake.util

import android.util.Log
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class CategoryTask {

    fun execute(url: String) {

        //Nesse momento, estamos utilizando a UI-Thread(1)
        val executor = Executors.newSingleThreadExecutor()
        executor.execute {

            try {

                // nesse momento estamos utilizado a NOVA_tread(processo paralelo)(2)
                val requestURL = URL(url) // Abrir uma url
                val urlConnection = requestURL.openConnection() as HttpURLConnection // abrir conexão
                urlConnection.readTimeout = 2000 // tempo de leitura 2s
                urlConnection.connectTimeout = 2000 // tempo de conexao 2s

                val statusCode = urlConnection.responseCode
                if (statusCode> 400){
                    throw  IOException("Erro na comunicação com o servidor!")
                }

                //Forma 1 : simples e rapida de abrir uma conexao HTTP

                val stream = urlConnection.inputStream  // Sequencias de Byte
                val jsonAsString = stream.bufferedReader().use { it.readText() } // transformar de byte para String

                Log.i("Teste", jsonAsString)

            } catch (e: IOException){
                Log.e("Teste", e.message?: " erro desconhecido", e)

            }



        }
    }
}