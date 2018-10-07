package com.example.nikhil.exo.presenter

import android.content.ComponentName
import android.net.Uri
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import com.example.nikhil.exo.model.MediaInteractor
import com.example.nikhil.exo.view.MainActivity
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util

class MediaPresenter(var view: MainActivity) {

    private var exoplayer: SimpleExoPlayer? = null
    private var playbackStateBuilder : PlaybackStateCompat.Builder? = null
    private var mediaSession: MediaSessionCompat? = null
    private val interactor = MediaInteractor()

    fun releasePlayer() {
        if (exoplayer != null) {
            exoplayer?.stop()
            exoplayer?.release()
            exoplayer = null
        }
    }

    fun prepareExoPlayer() {
        val trackSelector = DefaultTrackSelector()
        exoplayer = ExoPlayerFactory.newSimpleInstance(view, trackSelector)

        view.setExoPlayer(exoplayer)

        prepareMediaForExo()
        setupPlayerConfiguration()
    }

    private fun setupPlayerConfiguration(){
        val componentName = ComponentName(view, "Exo")
        mediaSession = MediaSessionCompat(view, "ExoPlayer", componentName, null)

        playbackStateBuilder = PlaybackStateCompat.Builder()

        playbackStateBuilder?.setActions(PlaybackStateCompat.ACTION_PLAY or PlaybackStateCompat.ACTION_PAUSE or
                PlaybackStateCompat.ACTION_FAST_FORWARD)

        mediaSession?.setPlaybackState(playbackStateBuilder?.build())
        mediaSession?.isActive = true
    }

    private fun prepareMediaForExo() {
        val mediaList = interactor.fetchMedia()

        // for now we know that this example has only one song
        val mediaSource = generateExtractorMediaSource(mediaList[0].uri)

        exoplayer?.prepare(mediaSource)
    }

    private fun generateExtractorMediaSource(mediaUri: Uri): ExtractorMediaSource{
        val userAgent = Util.getUserAgent(view, "Exo")
        return ExtractorMediaSource(mediaUri, DefaultDataSourceFactory(view, userAgent), DefaultExtractorsFactory(), null, null)
    }
}