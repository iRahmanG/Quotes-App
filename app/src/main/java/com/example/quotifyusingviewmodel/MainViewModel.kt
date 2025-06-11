package com.example.quotifyusingviewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import java.io.IOException

class MainViewModel(applicationContext: Context) : ViewModel() {
    private var quoteList: Array<Quote> = emptyArray()
    private var currentIndex = 0

    init {
        quoteList = loadQuotesFromAssets(applicationContext)
    }

    private fun loadQuotesFromAssets(context: Context): Array<Quote> {
        var inputStream: java.io.InputStream? = null
        try {
            inputStream = context.assets.open("quotes.json")
            val size: Int = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            val json = String(buffer, Charsets.UTF_8)
            val gson = Gson()
            return gson.fromJson(json, Array<Quote>::class.java) ?: emptyArray()
        } catch (e: IOException) {
            e.printStackTrace()
            return emptyArray()
        } finally {
            try {
                inputStream?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    fun getCurrentQuote(): Quote? {
        return if (quoteList.isNotEmpty()) {
            quoteList[currentIndex]
        } else {
            null
        }
    }

    fun nextQuote(): Quote? {
        if (quoteList.isEmpty()) {
            return null
        }
        currentIndex = (currentIndex + 1) % quoteList.size
        return quoteList[currentIndex]
    }

    fun previousQuote(): Quote? {
        if (quoteList.isEmpty()) {
            return null
        }
        currentIndex = (currentIndex - 1 + quoteList.size) % quoteList.size
        return quoteList[currentIndex]
    }
}