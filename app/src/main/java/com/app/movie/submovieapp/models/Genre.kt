package com.app.movie.submovieapp.models

import io.realm.RealmObject

open class Genre : RealmObject() {
    var id : Int = 0
    var name : String? = null
}