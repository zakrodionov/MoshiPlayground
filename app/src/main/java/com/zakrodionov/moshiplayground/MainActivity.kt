package com.zakrodionov.moshiplayground

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

/*
*https://github.com/square/moshi/#kotlin
*
* */
class MainActivity : AppCompatActivity() {

    private val simpleJson = "{\"name\":\"Toby\",\"age_old\":18}"
    private val simpleNullJson = "{\"name\":\"Toby\",\"age_old\":null}"
    private val simpleEmptyJson = "{\"name\":\"Toby\"}"

    private val simpleListJson = "[{\"name\":\"Toby\",\"age_old\":18}]"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        useMoshiCommonExt()
        //useMoshiCommon()
        //useMoshiWithKotlinReflection()
    }

    //perfecto
    private fun useMoshiCommonExt() {
        val moshi = Moshi.Builder().build()

        val jsonAdapter = moshi.adapter(Dog::class.java)

        val dog = moshi.fromJson<Dog>(simpleJson)
        val json = moshi.toJson<Dog>(dog)

        val dogs = moshi.fromJson<List<Dog>>(simpleListJson)
        val jsons = moshi.toJson<List<Dog>>(dogs)

        Log.d("moshi_test", "space \n model:: $dog \n json :: $json")
        Log.d("moshi_test", "space \n models:: $dogs \n jsons :: $jsons")
    }

    //good
    private fun useMoshiCommon() {
        val moshi = Moshi.Builder()
                .build()

        val jsonAdapter = moshi.adapter(Dog::class.java)

        val dog = jsonAdapter.fromJson(simpleNullJson)
        val json = jsonAdapter.toJson(dog)

        Log.d("moshi_test", "space \n model:: $dog ::\n json :: $json")
    }

    //bad because kotlin-reflect library 2.5 MiB .jar file
    private fun useMoshiWithKotlinReflection() {
        val moshi = Moshi.Builder()
                // ... add your own JsonAdapters and factories ...
                .add(KotlinJsonAdapterFactory())
                .build()
        val jsonAdapter: JsonAdapter<Dog> = moshi.adapter<Dog>(Dog::class.java)

        val dog = jsonAdapter.fromJson(simpleJson)
        val json = jsonAdapter.toJson(dog)

        Log.d("moshi_test", "space \n model:: $dog ::\n json :: $json")
    }
}

inline fun <reified T> Moshi.toJson(model: T?) = adapter(T::class.java).toJson(model)

inline fun <reified T> Moshi.fromJson(json: String?) = json?.let { adapter(T::class.java).fromJson(it) }