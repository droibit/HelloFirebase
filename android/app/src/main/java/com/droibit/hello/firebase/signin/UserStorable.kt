package com.droibit.hello.firebase.signin

import android.util.Log
import com.droibit.hello.firebase.model.User
import com.google.firebase.database.FirebaseDatabase

interface UserStorable {
    
    fun storeUser(userId: String, email: String)
}

class FirebaseUserStorableImpl : UserStorable {

    private val database = FirebaseDatabase.getInstance()

    override fun storeUser(userId: String, email: String) {
        val ref = database.reference
        val userName = extractLocalPart(email) ?: email
        val user = User(userName, email)
        Log.d("SignUp/In", "new user=$user")

        ref.child("users").child(userId).setValue(user)
                .addOnCompleteListener { task ->
                    Log.d("SignUp/In", "successful=${task.isSuccessful}, e=${task.exception?.message}")
                }
    }

    private fun extractLocalPart(email: String): String? {
        return if ("@" in email) email.split("@").first() else null
    }
}