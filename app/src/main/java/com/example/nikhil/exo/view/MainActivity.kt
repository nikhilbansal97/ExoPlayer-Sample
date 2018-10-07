package com.example.nikhil.exo.view

import android.content.ComponentName
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import com.example.nikhil.exo.R
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.SimpleExoPlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util

class MainActivity : AppCompatActivity() {

    private var exoplayerView : SimpleExoPlayerView? = null
    private var exoplayer : SimpleExoPlayer? = null
    private var playbackStateBuilder : PlaybackStateCompat.Builder? = null
    private var mediaSession: MediaSessionCompat? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        exoplayerView = findViewById(R.id.simpleExoPlayerView)
        initializePlayer()
    }

    private fun initializePlayer() {
        val trackSelector = DefaultTrackSelector()
        exoplayer = ExoPlayerFactory.newSimpleInstance(baseContext, trackSelector)
        exoplayerView?.player = exoplayer

        val userAgent = Util.getUserAgent(baseContext, "Exo")
        val mediaUri = Uri.parse("asset:///heart_attack.mp3")
        val mediaSource = ExtractorMediaSource(mediaUri, DefaultDataSourceFactory(baseContext, userAgent), DefaultExtractorsFactory(), null, null)

        exoplayer?.prepare(mediaSource)

        val componentName = ComponentName(baseContext, "Exo")
        mediaSession = MediaSessionCompat(baseContext, "ExoPlayer", componentName, null)

        playbackStateBuilder = PlaybackStateCompat.Builder()

        playbackStateBuilder?.setActions(PlaybackStateCompat.ACTION_PLAY or PlaybackStateCompat.ACTION_PAUSE or
                                            PlaybackStateCompat.ACTION_FAST_FORWARD)

        mediaSession?.setPlaybackState(playbackStateBuilder?.build())
        mediaSession?.isActive = true
    }
//
//    override fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters?) {
//    }
//
//    override fun onSeekProcessed() {
//    }
//
//    override fun onTracksChanged(trackGroups: TrackGroupArray?, trackSelections: TrackSelectionArray?) {
//    }
//
//    override fun onPlayerError(error: ExoPlaybackException?) {
//    }
//
//    override fun onLoadingChanged(isLoading: Boolean) {
//    }
//
//    override fun onPositionDiscontinuity(reason: Int) {
//    }
//
//    override fun onRepeatModeChanged(repeatMode: Int) {
//    }
//
//    override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {
//    }
//
//    override fun onTimelineChanged(timeline: Timeline?, manifest: Any?) {
//    }
//
//    override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
//        if (playWhenReady)
//            playbackStateBuilder?.setState(PlaybackStateCompat.STATE_PLAYING, exoplayer?.currentPosition!!, 1f)
//        else
//            playbackStateBuilder?.setState(PlaybackStateCompat.STATE_PAUSED, exoplayer?.currentPosition!!, 1f)
//        mediaSession?.setPlaybackState(playbackStateBuilder?.build())
//    }

    override fun onDestroy() {
        super.onDestroy()
        releasePlayer()
    }

    private fun releasePlayer() {
        if (exoplayer != null) {
            exoplayer?.stop()
            exoplayer?.release()
            exoplayer = null
        }
    }
}
