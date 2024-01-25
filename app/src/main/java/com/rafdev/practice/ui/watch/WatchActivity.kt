package com.digitalproserver.android_latina.ui.watch

import android.content.pm.ActivityInfo
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import com.digitalproserver.android_latina.R
import com.digitalproserver.android_latina.databinding.ActivityWatchBinding
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WatchActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWatchBinding
    private lateinit var exoPlayer: SimpleExoPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWatchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val receivedLink = intent.getStringExtra("VIDEO_LINK")
        val receivedTitle = intent.getStringExtra("VIDEO_TITLE")

        makeActivityFullScreen()
        setupViews(receivedTitle)
        setupExoPlayer(receivedLink)
    }

    private fun makeActivityFullScreen() {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        window.decorView.apply {
            systemUiVisibility = (
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                            or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            or View.SYSTEM_UI_FLAG_FULLSCREEN
                    )
        }
    }

    private fun setupViews(title: String?) {
        val titleTextView = findViewById<TextView>(R.id.exo_title)
        val backButton = findViewById<ImageView>(R.id.backButton)
        val fullscreenButton = findViewById<ImageView>(R.id.bt_fullscreen)

        fullscreenButton .setImageResource(R.drawable.ic_baseline_fullscreen_exit)
        backButton.setOnClickListener { onBackPressed() }
        fullscreenButton.setOnClickListener { onBackPressed() }
        titleTextView.text = title
    }

    private fun setupExoPlayer(m3u8: String?) {
        exoPlayer = SimpleExoPlayer.Builder(this)
            .setSeekBackIncrementMs(10000)
            .setSeekForwardIncrementMs(10000)
            .build()

        binding.player.player = exoPlayer
        binding.player.keepScreenOn = true

        exoPlayer.addListener(object : Player.Listener {
            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                binding.progressBar.visibility =
                    if (playbackState == Player.STATE_BUFFERING) View.VISIBLE else View.GONE
            }
        })

        val videoSource = Uri.parse(m3u8)
        val mediaItem = MediaItem.fromUri(videoSource)
        exoPlayer.setMediaItem(mediaItem)
        exoPlayer.prepare()
        exoPlayer.play()
    }

    override fun onStop() {
        super.onStop()
        exoPlayer.stop()
    }

    override fun onDestroy() {
        super.onDestroy()
        exoPlayer.release()
    }

    override fun onPause() {
        super.onPause()
        exoPlayer.pause()
    }
}
