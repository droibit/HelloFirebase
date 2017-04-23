package com.droibit.hello.firebase.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Comment(var uid: String, var author: String, var text: String) {

    constructor() : this(uid = "", author = "", text = "")
}
