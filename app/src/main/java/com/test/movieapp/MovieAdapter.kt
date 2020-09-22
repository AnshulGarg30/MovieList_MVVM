package com.test.movieapp

import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item_movie.view.*


class MovieAdapter(val context: Context, val list:ArrayList<MovieList>): RecyclerView.Adapter<MovieAdapter.ViewHolder>(){
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imagePoster=itemView.image_poster
        val textTitle = itemView.text_title
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_movie, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textTitle.text = list[position].name
        val uri: String = "@drawable/${list[position].image}"
        val resources: Resources = context.resources
        val resourceId: Int = resources.getIdentifier(
            list[position].image.replace(".jpg",""), "drawable",
            context.packageName
        )

        holder.imagePoster.setImageDrawable(ContextCompat.getDrawable(context,resourceId))

    }

}