package com.example.nikhil.exo

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_HIGH
import android.app.PendingIntent
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.example.nikhil.exo.databinding.ActivityMainBinding
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerNotificationManager
import com.google.android.exoplayer2.util.NotificationUtil

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var exoplayer: SimpleExoPlayer
    private lateinit var playerNotificationManager: PlayerNotificationManager

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

        // Initialize PlayerNotificationManager
        initPlayerNotificationManager()

        // Set the exoPlayer to the playerView
        binding.playerView.player = exoplayer

        // Create a MediaItem
        val mediaItem = createMediaItem()

        exoplayer.addMediaItem(mediaItem)
        exoplayer.prepare()
        exoplayer.play()
    }

    private fun createMediaItem(): MediaItem {
        val mediaUri = Uri.parse("asset:///heart_attack.mp3")
        return MediaItem.fromUri(mediaUri)
    }

    private fun initPlayerNotificationManager() {
        val channel = NotificationChannel(Constants.NOTIFICATION_CHANNEL, Constants.NOTIFICATION_CHANNEL, IMPORTANCE_HIGH)
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)

        playerNotificationManager = PlayerNotificationManager.Builder(this,
            Constants.NOTIFICATION_ID,
            Constants.NOTIFICATION_CHANNEL,
            object : PlayerNotificationManager.MediaDescriptionAdapter {
                override fun getCurrentContentTitle(player: Player): CharSequence =
                    player.currentMediaItem?.mediaMetadata?.title ?: "Music"

                override fun createCurrentContentIntent(player: Player): PendingIntent? {
                    return null
                }

                override fun getCurrentContentText(player: Player): CharSequence? {
                    return "Music Content Text"
                }

                override fun getCurrentLargeIcon(
                    player: Player,
                    callback: PlayerNotificationManager.BitmapCallback
                ): Bitmap? {
                    return BitmapFactory.decodeResource(applicationContext.resources, R.drawable.ic_play_button)
                }

            })
            .build()
        playerNotificationManager.setPlayer(exoplayer)
    }

    override fun onDestroy() {
        super.onDestroy()
        releasePlayer()
    }

    private fun releasePlayer() {
        exoplayer.stop()
        playerNotificationManager.setPlayer(null)
        exoplayer.release()
    }
}
