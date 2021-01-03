package com.thecodingshef.socialmedia

import android.app.DownloadManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.Query
import com.thecodingshef.socialmedia.Dao.PostDao
import com.thecodingshef.socialmedia.Model.Post
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

   private lateinit var postAdapter:PostAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        floatingActionButton.setOnClickListener {

            val intent=Intent(this,CreatePostActivity::class.java)
            startActivity(intent)
        }

        setupRecyclerview()

    }

    private fun setupRecyclerview() {

        val postDao=PostDao()
        val query=postDao.postCollection.orderBy("createdBy",Query.Direction.DESCENDING)

        val recyclerOptions=FirestoreRecyclerOptions.Builder<Post>().setQuery(query,Post::class.java).build()
        postAdapter= PostAdapter(recyclerOptions)

        recyclerView.adapter=postAdapter
        recyclerView.layoutManager=LinearLayoutManager(this)

        recyclerView.setHasFixedSize(true)

    }

    override fun onStart() {
        super.onStart()
        postAdapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        postAdapter.stopListening()
    }
}