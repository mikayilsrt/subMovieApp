package com.app.movie.submovieapp.models

import io.realm.RealmList
import io.realm.RealmObject

open class Movie : RealmObject() {
    var id : Int = 0
    var title : String? = null
    var overview : String? = null
    var backdrop_path : String? = null
    var poster_path : String? = null
    var genres : RealmList<Genre>? = null
    var isFavorite : Boolean = false
}