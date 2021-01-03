package com.thecodingshef.socialmedia.Dao

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.thecodingshef.socialmedia.Model.Post
import com.thecodingshef.socialmedia.Model.User
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class PostDao {

    val db=FirebaseFirestore.getInstance()
    val postCollection=db.collection("posts")
    val auth=Firebase.auth

    fun addPost(text:String,context: Context){

        val currentUserId=auth.currentUser!!.uid

        GlobalScope.launch {
            val userDao=UserDao()
            val user=userDao.getUserById(currentUserId) .await().toObject(User::class.java)!!

            val currentTime=System.currentTimeMillis()
            val post=Post(text,user,currentTime)

            postCollection.document().set(post)


        }

    }

    fun getAllPost(){

        postCollection.document().get()
    }
}