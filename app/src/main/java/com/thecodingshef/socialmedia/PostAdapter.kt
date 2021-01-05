package com.thecodingshef.socialmedia

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.squareup.okhttp.internal.DiskLruCache
import com.thecodingshef.socialmedia.Model.Post
import kotlinx.android.synthetic.main.post_item.view.*

class PostAdapter(options: FirestoreRecyclerOptions<Post>, val listener: PostClickListener) :
    FirestoreRecyclerAdapter<Post, PostAdapter.PostViewHolder>(options) {


    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val postText: TextView = itemView.findViewById(R.id.postTitle)
        val userText: TextView = itemView.findViewById(R.id.userName)
        val createdAt: TextView = itemView.findViewById(R.id.createdAt)
        val likeCount: TextView = itemView.findViewById(R.id.likeCount)
        val userImage: ImageView = itemView.findViewById(R.id.userImage)
        val likeButton: ImageView = itemView.findViewById(R.id.likeButton)

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):PostViewHolder {

        val viewHolder=PostViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.post_item,parent,false))

        viewHolder.likeButton.setOnClickListener{

            listener.onLikeClick(snapshots.getSnapshot(viewHolder.adapterPosition).id)
        }

        return viewHolder
    }


    override fun onBindViewHolder(holder: PostViewHolder, position: Int, model: Post) {


        holder.postText.text=model.text
        holder.userText.text=model.createdBy.userName
        holder.createdAt.text=Utils.getTimeAgo(model.createdAt)
        Glide.with(holder.userImage.context).load(model.createdBy.imageUrl).circleCrop().into(holder.userImage)
        holder.likeCount.text=model.likedBy.size.toString()


        val auth = Firebase.auth
        val currentUserId = auth.currentUser!!.uid
        val isLiked = model.likedBy.contains(currentUserId)
        if(isLiked) {
            holder.likeButton.setImageDrawable(ContextCompat.getDrawable(holder.likeButton.context, R.drawable.ic_liked))
        } else {
            holder.likeButton.setImageDrawable(ContextCompat.getDrawable(holder.likeButton.context, R.drawable.ic_unliked))
        }


    }
}

interface PostClickListener{

    fun onLikeClick(postId:String)
}