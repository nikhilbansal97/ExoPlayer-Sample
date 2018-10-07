package com.example.nikhil.exo.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.nikhil.exo.R
import com.example.nikhil.exo.presenter.MediaPresenter
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.ui.SimpleExoPlayerView

class MainActivity : AppCompatActivity() {

    private var mediaPresenter: MediaPresenter = MediaPresenter(this)
    private var exoplayerView : SimpleExoPlayerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        exoplayerView = findViewById(R.id.simpleExoPlayerView)
        initializePlayer()
    }

    private fun initializePlayer() {
        mediaPresenter.prepareExoPlayer()
    }

    fun setExoPlayer(exoPlayer: SimpleExoPlayer?) {
        exoplayerView?.player = exoPlayer
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
        mediaPresenter.releasePlayer()
    }
}
