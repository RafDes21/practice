package com.example.android_americavivo.ui.watch

import android.content.pm.ActivityInfo
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.android_americavivo.R
import com.example.android_americavivo.databinding.ActivityMainBinding
import com.example.android_americavivo.databinding.ActivityWatchBinding
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WatchActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWatchBinding
    private lateinit var simpleExoPlayer: SimpleExoPlayer

    private var isFullScreen = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWatchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val receivedKeyName = intent.getStringExtra("KEY_NAME")
        val urlVod = intent.getStringExtra("VOD")


        val playerView = binding.player
        val progressBar = binding.progressBar
        val bt_fullscreen = findViewById<ImageView>(R.id.bt_fullscreen)
        val title = findViewById<TextView>(R.id.exo_title)

        title.text = receivedKeyName

        val backButton = findViewById<ImageView>(R.id.backButton)
        backButton.setOnClickListener {
            onBackPressed()
        }

        bt_fullscreen.setOnClickListener{
            if (!isFullScreen){
                bt_fullscreen.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.ic_baseline_fullscreen_exit))
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
            }else{
                bt_fullscreen.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.ic_baseline_fullscreen))
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
            }
            isFullScreen = !isFullScreen
        }

        simpleExoPlayer= SimpleExoPlayer.Builder(this)
            .setSeekBackIncrementMs(10000)
            .setSeekForwardIncrementMs(10000)
            .build()

        playerView.player = simpleExoPlayer
        playerView.keepScreenOn = true
        simpleExoPlayer.addListener(object :Player.Listener{
            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                if (playbackState == Player.STATE_BUFFERING){
                    progressBar.visibility = View.VISIBLE
                }else if(playbackState == Player.STATE_READY){
                    progressBar.visibility = View.GONE
                }
            }
        })
//        val videoSource = Uri.parse("https://redirector.dps.live/hls/ecuavisa2/playlist.m3u8")
        val videoSource = Uri.parse(urlVod )
//        val videoSource = Uri.parse("https://www.rmp-streaming.com/media/big-buck-bunny-360p.mp4")
        val mediaItem = MediaItem.fromUri(videoSource)
        simpleExoPlayer.setMediaItem(mediaItem)
        simpleExoPlayer.prepare()
        simpleExoPlayer.play()
    }

    override fun onStop() {
        super.onStop()
        simpleExoPlayer.stop()
    }

    override fun onDestroy() {
        super.onDestroy()
        simpleExoPlayer.release()
    }

    override fun onPause() {
        super.onPause()
        simpleExoPlayer.pause()
    }
}