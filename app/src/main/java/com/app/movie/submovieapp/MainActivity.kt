package com.app.movie.submovieapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import io.realm.Realm
import io.realm.RealmConfiguration

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val realmInstance : Realm = this.getRealmInstance()
    }

    /**
     * Initialise Realm Configuration and get Realm Instance
     *
     * @return Realm
     */
    private fun getRealmInstance () : Realm {
        Realm.init(this)
        val realmConfiguration : RealmConfiguration = RealmConfiguration.Builder()
            .name("movie.realm")
            .build()
        return Realm.getInstance(realmConfiguration)
    }
}