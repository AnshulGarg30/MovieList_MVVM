package com.test.movieapp

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuItemCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    
    lateinit var list:ArrayList<MovieList>
    lateinit var context: Context
    private lateinit var viewModel: MainActivityViewModel
    lateinit var manager:GridLayoutManager
    lateinit var movieAdapter:MovieAdapter
    var index=1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        context=this
        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        val orientation = this.resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            manager= GridLayoutManager(this,3)
        } else {
            manager = GridLayoutManager(this,5)
        }

        recyclerviewmovies.layoutManager=manager
        recyclerviewmovies.addItemDecoration(
            ItemDecorationAlbumColumns(
                resources.getDimensionPixelSize(R.dimen.photos_list_spacing),
                resources.getInteger(R.integer.photo_list_preview_columns)
            )
        )
        list=ArrayList()
        list=viewModel.readJSONFromAsset(list,this,index)
        movieAdapter=MovieAdapter(this,list)
        recyclerviewmovies.adapter=movieAdapter
        recyclerviewmovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as GridLayoutManager?
                val visibleItemCount = layoutManager!!.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
                if(totalItemCount%20==0)
                {
                    index++
                    list=viewModel.readJSONFromAsset(list,context,index)
                    movieAdapter.notifyDataSetChanged()
                }
            }

        })

    }
    private lateinit var searchView: SearchView

    /*search icon is shown on toolbar main*/
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search, menu)
        val searchmenuitem=menu.findItem(R.id.search)
        searchView = searchmenuitem.actionView as SearchView
        MenuItemCompat.setOnActionExpandListener(searchmenuitem,object:MenuItemCompat.OnActionExpandListener{
            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                list.clear()
                index=1
                list=viewModel.readJSONFromAsset(list,context,index)
                return true
            }

        })

        searchView.apply {
            queryHint = "Search"
            isSubmitButtonEnabled = true
            onActionViewExpanded()
        }
        search(searchView)

        return true
    }

    /*search quesry data is fetched*/
    private fun search(searchView: SearchView) {

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                dismissKeyboard(searchView)
                searchView.clearFocus()
                list .clear()
                index=1
                viewModel.searchMovie(list,query,context)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
    }

}