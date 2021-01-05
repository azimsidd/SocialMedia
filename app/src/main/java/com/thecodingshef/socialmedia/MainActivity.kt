package com.thecodingshef.socialmedia

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.Query
import com.google.firebase.ktx.Firebase
import com.thecodingshef.socialmedia.Dao.PostDao
import com.thecodingshef.socialmedia.Model.Post
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), PostClickListener {

   private lateinit var postAdapter:PostAdapter
   private lateinit var  postDao:PostDao
   private lateinit var mGoogleApiClient:GoogleApiClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        floatingActionButton.setOnClickListener {

            val intent=Intent(this, CreatePostActivity::class.java)
            startActivity(intent)
        }

        setupRecyclerview()


    }

    private fun setupRecyclerview() {

         postDao=PostDao()
        val query=postDao.postCollection.orderBy("createdBy", Query.Direction.DESCENDING)

        val recyclerOptions=FirestoreRecyclerOptions.Builder<Post>().setQuery(query, Post::class.java).build()
        postAdapter= PostAdapter(recyclerOptions, this)

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

    override fun onLikeClick(postId: String) {
        postDao.updateLike(postId)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.logout_menu, menu)
        return super.onCreateOptionsMenu(menu)


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {


       /* when(item.itemId){
            R.id.userLogout-> Toast.makeText(this,"Logout",Toast.LENGTH_SHORT).show()

        }*/

        if(item.itemId.equals(R.id.userLogout)){
            Firebase.auth.signOut()
            startActivity(Intent(this,SignInActivity::class.java))
            finish()
            Toast.makeText(this, "You have been Logout", Toast.LENGTH_SHORT).show()
        }


        return super.onOptionsItemSelected(item)

    }
}