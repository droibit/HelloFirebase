package com.droibit.hello.firebase.model

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Post(
        var uid: String,
        var author: String,
        var title: String,
        var body: String,
        var starCount: Int, val stars: Map<String, Boolean>) {

    constructor() :
            this(uid = "", author = "", title = "", body = "", starCount = 0, stars = hashMapOf())


    constructor(uid: String, author: String, title: String, body: String) :
            this(uid, author, title, body, starCount = 0, stars = hashMapOf())


    @Exclude
    fun toMap() = hashMapOf(
            Post::uid.name to uid,
            Post::author.name to author,
            Post::title.name to title,
            Post::body.name to body,
            Post::starCount.name to starCount,
            Post::stars.name to stars
    )
}