package com.plantflashcards.plantflashcards.dao

import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLConnection

/**
 * Created by jonesb on 4/24/2015.
 */
class NetworkingDAO {

    /**
     * Execute the given URI, and return the data from that URI.
     * @param uri the universal resource indicator for a set of data.
     * *
     * @return the set of data provided by the uri
     */
    @Throws(IOException::class)
    fun request(uri: String): String {
        val sb = StringBuilder()

        val url = URL(uri)
        val urlConnection = url.openConnection() as HttpURLConnection
        try {
            val bis = BufferedInputStream(urlConnection.inputStream)
            val bin = BufferedReader(InputStreamReader(bis))
            // temporary string to hold each line read from the reader.
            var inputLine: String?
            inputLine = bin.readLine()
            while (inputLine != null) {
                sb.append(inputLine)
                inputLine = bin.readLine()
            }
        } finally {
            // regardless of success or failure, we will disconnect from the URLConnection.
            urlConnection.disconnect()
        }
        return sb.toString()
    }

}