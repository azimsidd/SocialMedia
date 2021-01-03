package com.thecodingshef.socialmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.thecodingshef.socialmedia.Dao.PostDao
import kotlinx.android.synthetic.main.activity_create_post.*

class CreatePostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_post)

        postBtn.setOnClickListener {
            val postData=textInput.text.toString().trim()

            if(postData.isNotEmpty()){

                val postDao=PostDao()
                postDao.addPost(postData,this)
                finish()


            }
        }
    }
}