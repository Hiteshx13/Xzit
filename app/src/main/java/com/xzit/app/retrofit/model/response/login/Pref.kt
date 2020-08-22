package com.xzit.app.retrofit.model.response.login

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class Pref{

    @SerializedName("music")
    @Expose
    private var music: List<Any?>? = null

    @SerializedName("venue")
    @Expose
    private var venue: List<Any?>? = null

    @SerializedName("food")
    @Expose
    private var food: List<Any?>? = null

    fun getMusic(): List<Any?>? {
        return music
    }

    fun setMusic(music: List<Any?>?) {
        this.music = music
    }

    fun getVenue(): List<Any?>? {
        return venue
    }

    fun setVenue(venue: List<Any?>?) {
        this.venue = venue
    }

    fun getFood(): List<Any?>? {
        return food
    }

    fun setFood(food: List<Any?>?) {
        this.food = food
    }

}