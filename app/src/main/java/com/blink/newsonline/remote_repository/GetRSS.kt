package com.blink.newsonline.remote_repository

import android.os.AsyncTask
import com.blink.newsonline.activity.RssResponde
import com.blink.newsonline.model.News
import com.blink.newsonline.util.XMLParser
import org.w3c.dom.Element
import org.w3c.dom.NodeList
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL

class GetRSS(private val rssResponde: RssResponde) : AsyncTask<String, Void, String>() {
    private lateinit var linkRss: URL
    private lateinit var listNews: ArrayList<News>

    override fun onPreExecute() {
        super.onPreExecute()
        listNews = ArrayList()
    }

    override fun doInBackground(vararg params: String?): String {
        linkRss = URL(params[0])

        val inputStreamReader = InputStreamReader(linkRss.openConnection().getInputStream())
        val bufferedReader = BufferedReader(inputStreamReader)

        val content  = StringBuilder()
        var line: String?

        while ((bufferedReader.readLine().also { line = it }) != null) {
            content.append(line)
        }

        bufferedReader.close()
        inputStreamReader.close()

        return content.toString()
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
        val xmlParser = XMLParser()
        val document = xmlParser.getDocument(result)
        val nodeList: NodeList = document.getElementsByTagName("item")
        val length: Short = (nodeList.length - 1).toShort()

        for (i in 0..length) {
            val element: Element = nodeList.item(i) as Element
            val data = xmlParser.getImgSource(xmlParser.getValue(element, "description"))
            listNews.add(
                News(data[0], xmlParser.getValue(element, "title"),
                xmlParser.getValue(element, "pubDate"),
                data[1],
                xmlParser.getValue(element, "link"))
            )
        }
        rssResponde.onAsyncComplete(listNews)
    }
}