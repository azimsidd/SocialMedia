package com.thecodingshef.socialmedia.Dao

import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.thecodingshef.socialmedia.Model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UserDao {

    private val firebaseDb=FirebaseFirestore.getInstance()
    private val userCollections=firebaseDb.collection("users")

    fun addUser(user:User?){

        user?.let {

            GlobalScope.launch(Dispatchers.IO){
                userCollections.document(user.userId).set(it)
            }

        }
    }

    fun getUserById(uId:String):Task<DocumentSnapshot>{

        return userCollections.document(uId).get()
    }
}