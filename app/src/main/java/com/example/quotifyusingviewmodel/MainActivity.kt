package com.example.quotifyusingviewmodel

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel

    private val quoteText: TextView
        get() = findViewById(R.id.quoteText)
    private val quoteAuthor: TextView
        get() = findViewById(R.id.quoteAuthor)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        mainViewModel = ViewModelProvider(this, MainViewModelFactory(applicationContext)).get(MainViewModel::class.java)
        displayQuote(mainViewModel.getCurrentQuote())
    }

    private fun displayQuote(quote: Quote?) {
        if (quote != null) {
            quoteText.text = quote.text
            quoteAuthor.text = quote.author
        } else {
            quoteText.text = "No quotes available."
            quoteAuthor.text = ""
        }
    }

    fun onPrevious(view: View) {
        displayQuote(mainViewModel.previousQuote())
    }

    fun onNext(view: View) {
        displayQuote(mainViewModel.nextQuote())
    }

    fun onShare(view: View) {
        val currentQuote = mainViewModel.getCurrentQuote()
        if (currentQuote != null) {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, "${currentQuote.text} - ${currentQuote.author}")
            startActivity(Intent.createChooser(intent, "Share Quote Via")) // Use a chooser
        } else {
            Toast.makeText(this, "No quote to share", Toast.LENGTH_SHORT).show()
        }
    }
}