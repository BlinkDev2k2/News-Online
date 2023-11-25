package com.blink.newsonline.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.blink.newsonline.R
import com.blink.newsonline.model.News
import com.bumptech.glide.Glide

class NewsAdapter(private val context: Context, val data: ArrayList<News>) : BaseAdapter() {
    override fun getCount(): Int {
        return data.size
    }

    override fun getItem(position: Int): Any {
        return data[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("InflateParams")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view: View? = convertView
        val viewHolder: ViewHolder
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_news, parent, false)
            viewHolder = ViewHolder()
            viewHolder.title = view.findViewById(R.id.tvTitle)
            viewHolder.pubDate = view.findViewById(R.id.tvPubDate)
            viewHolder.img = view.findViewById(R.id.imgNews)
            viewHolder.description = view.findViewById(R.id.tvDescription)
            view.tag = viewHolder
        } else {
            viewHolder = view?.tag as ViewHolder
        }
        viewHolder.title.text = data[position].title
        viewHolder.pubDate.text = data[position].pubDate
        viewHolder.description.text = data[position].description
        Log.d("AA", data[position].img)
        Glide.with(context).load(data[position].img).error(R.drawable.vn_express).into(viewHolder.img)
        view?.startAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_item_news))
        return view!!
    }

    companion object {
        class ViewHolder {
            internal lateinit var title: TextView
            internal lateinit var pubDate: TextView
            internal lateinit var img: ImageView
            internal lateinit var description: TextView
        }
    }
}