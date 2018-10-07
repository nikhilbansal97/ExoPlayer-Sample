package com.example.nikhil.exo.model

import android.net.Uri

class MediaInteractor(){

    fun fetchMedia(): List<Media> {
        return generateMedia()
    }

    private fun generateMedia(): List<Media> {
        val mediaList = ArrayList<Media>()
        val mediaUri = Uri.parse("asset:///heart_attack.mp3")
        mediaList.add(Media(mediaUri))

        return mediaList
    }
}