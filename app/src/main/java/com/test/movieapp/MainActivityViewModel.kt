package com.test.movieapp

import android.content.Context
import androidx.lifecycle.ViewModel
import org.json.JSONObject
import java.io.InputStream


class MainActivityViewModel :ViewModel()
{

    /*main screen data is fetched from json files and stored in arraylist*/
    fun readJSONFromAsset(list:ArrayList<MovieList>,context:Context,index:Int) :ArrayList<MovieList>{

        var json: String? = null
        try {
            var  inputStream: InputStream
            if(index==1)
                inputStream = context.assets.open("CONTENTLISTINGPAGE-PAGE1.json")
            else if(index==2)
                inputStream = context.assets.open("CONTENTLISTINGPAGE-PAGE2.json")
            else
                inputStream = context.assets.open("CONTENTLISTINGPAGE-PAGE3.json")

            json = inputStream.bufferedReader().use{it.readText()}
        } catch (ex: Exception) {
            ex.printStackTrace()
            return list
        }

        val obj= JSONObject(json)
        for(i in 0 until obj.getJSONObject("page").getJSONObject("content-items").getJSONArray("content").length())
        {
            list.add(
                MovieList(
                    obj.getJSONObject("page").getJSONObject("content-items")
                        .getJSONArray("content").getJSONObject(i).getString("name"),
                    obj.getJSONObject("page").getJSONObject("content-items")
                        .getJSONArray("content").getJSONObject(i).getString("poster-image")
                )
            )
        }

        return list
    }

    fun searchMovie(list:ArrayList<MovieList>,query: String,context: Context) {
        searchFromList(list,context,query,"search")
    }

    /*data is stored in single list and then filtered data according to query by user*/
    var searchList = ArrayList<MovieList>()
    private fun searchFromList(list: ArrayList<MovieList>, context: Context, query: String, s: String):ArrayList<MovieList>{
        var json: String? = null
        var json2: String? = null
        var json3: String? = null

        try {
            var  inputStream: InputStream = context.assets.open("CONTENTLISTINGPAGE-PAGE1.json")

            var inputStream2:InputStream = context.assets.open("CONTENTLISTINGPAGE-PAGE2.json")

            var inputStream3:InputStream = context.assets.open("CONTENTLISTINGPAGE-PAGE3.json")

            json = inputStream.bufferedReader().use{it.readText()}
            json2 = inputStream2.bufferedReader().use{it.readText()}
            json3 = inputStream3.bufferedReader().use{it.readText()}
        } catch (ex: Exception) {
            ex.printStackTrace()
            return list
        }

        val obj= JSONObject(json)
        for(i in 0 until obj.getJSONObject("page").getJSONObject("content-items").getJSONArray("content").length())
        {
            searchList.add(
                MovieList(
                    obj.getJSONObject("page").getJSONObject("content-items")
                        .getJSONArray("content").getJSONObject(i).getString("name"),
                    obj.getJSONObject("page").getJSONObject("content-items")
                        .getJSONArray("content").getJSONObject(i).getString("poster-image")
                )
            )

        }
        val obj2= JSONObject(json2)
        for(i in 0 until obj2.getJSONObject("page").getJSONObject("content-items").getJSONArray("content").length())
        {
            searchList.add(
                MovieList(
                    obj2.getJSONObject("page").getJSONObject("content-items")
                        .getJSONArray("content").getJSONObject(i).getString("name"),
                    obj2.getJSONObject("page").getJSONObject("content-items")
                        .getJSONArray("content").getJSONObject(i).getString("poster-image")
                )
            )

        }
        val obj3= JSONObject(json3)
        for(i in 0 until obj3.getJSONObject("page").getJSONObject("content-items").getJSONArray("content").length())
        {
            searchList.add(
                MovieList(
                    obj3.getJSONObject("page").getJSONObject("content-items")
                        .getJSONArray("content").getJSONObject(i).getString("name"),
                    obj3.getJSONObject("page").getJSONObject("content-items")
                        .getJSONArray("content").getJSONObject(i).getString("poster-image")
                )
            )

        }

        for(i in 0 until searchList.size)
        {
            if(searchList[i].name.contains(query))
            {
                list.add(MovieList(searchList[i].name,searchList[i].image))
            }
        }

        return list

    }
}