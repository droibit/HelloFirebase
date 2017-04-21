package com.droibit.hello.firebase.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class User(var userName: String = "", var email: String = "")