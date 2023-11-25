package com.blink.newsonline.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.blink.newsonline.remote_repository.GetRSS
import com.blink.newsonline.R
import com.blink.newsonline.adapter.NewsAdapter
import com.blink.newsonline.model.News

class MainActivity : AppCompatActivity(), RssResponde {
    private lateinit var listViewNews: ListView
    private lateinit var adapter: NewsAdapter

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val getRSS = GetRSS(this)
        getRSS.execute("https://vnexpress.net/rss/khoa-hoc.rss")
    }

    override fun onAsyncComplete(listNews: ArrayList<News>) {
        listViewNews = findViewById(R.id.listNews)
        adapter = NewsAdapter(this, listNews)
        listViewNews.adapter = adapter

        listViewNews.setOnItemClickListener { _, _, position, _ ->
            val intent = Intent(this, DetailNewsActivity::class.java)
            intent.putExtra("LINK_NEWS", listNews[position].link)
            startActivity(intent)
        }
    }
}

interface RssResponde {
    fun onAsyncComplete(listNews: ArrayList<News>)
}