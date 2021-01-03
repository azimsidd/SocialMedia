package com.thecodingshef.socialmedia

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.thecodingshef.socialmedia.Model.Post

class PostAdapter(options: FirestoreRecyclerOptions<Post>) :
    FirestoreRecyclerAdapter<Post, PostAdapter.PostViewHolder>(options) {


    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val postText: TextView = itemView.findViewById(R.id.postTitle)
        val userText: TextView = itemView.findViewById(R.id.userName)
        val createdAt: TextView = itemView.findViewById(R.id.createdAt)
        val likeCount: TextView = itemView.findViewById(R.id.likeCount)
        val userImage: ImageView = itemView.findViewById(R.id.userImage)
        val likeButton: ImageView = itemView.findViewById(R.id.likeButton)

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostAdapter.PostViewHolder {

        val view=LayoutInflater.from(parent.context).inflate(R.layout.post_item,parent,false)

        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostAdapter.PostViewHolder, position: Int, model: Post) {


        holder.postText.text=model.text
        holder.userText.text=model.createdBy.userName
        holder.createdAt.text=Utils.getTimeAgo(model.createdAt)
        Glide.with(holder.userImage.context).load(model.createdBy.imageUrl).circleCrop().into(holder.userImage)
        holder.likeCount.text=model.likedBy.size.toString()
    }
}