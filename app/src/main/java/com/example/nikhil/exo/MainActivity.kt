package com.example.nikhil.exo

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.example.nikhil.exo.databinding.ActivityMainBinding
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer

class MainActivity : AppCompatActivity() {

    private lateinit var exoplayer : SimpleExoPlayer
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        initializePlayer()
    }

    private fun initializePlayer() {
        // Initialize ExoPlayer
        exoplayer = SimpleExoPlayer.Builder(this)
            .build()
        binding.playerView.player = exoplayer

        // Create a MediaItem
        val mediaUri = Uri.parse("asset:///heart_attack.mp3")
        val mediaItem = MediaItem.fromUri(mediaUri)

        exoplayer.addMediaItem(mediaItem)
        exoplayer.prepare()
        exoplayer.play()
    }

    override fun onDestroy() {
        super.onDestroy()
        releasePlayer()
    }

    private fun releasePlayer() {
        exoplayer.stop()
        exoplayer.release()
    }
}
