package com.digitalproserver.android_latina.ui.watch

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.digitalproserver.android_latina.R
import com.digitalproserver.android_latina.databinding.FragmentWatchBinding
import com.digitalproserver.android_latina.ui.watch.observer.PlayerStatePublisher
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WatchFragment : Fragment() {

    private var _binding: FragmentWatchBinding? = null
    private val binding get() = _binding!!

    private lateinit var simpleExoPlayer: SimpleExoPlayer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWatchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val receivedTitle = arguments?.getString("VIDEO_TITLE")
        val receivedLink = arguments?.getString("VIDEO_LINK")
        adjustElementsStyles()
        initializePlayer(receivedLink)
        setupFullscreenButton(receivedLink, receivedTitle)
    }

    private fun adjustElementsStyles() {
        val playerView = binding.player
        val progressBar = binding.progressBar
        val layoutParams = progressBar.layoutParams

        layoutParams.width = 100
        layoutParams.height = 100
        progressBar.layoutParams = layoutParams

        val backBottom = playerView.findViewById<ImageView>(R.id.backButton)
        backBottom.visibility = View.INVISIBLE

    }

    private fun initializePlayer(m3u8: String?) {
        val playerView = binding.player
        val progressBar = binding.progressBar

        simpleExoPlayer = SimpleExoPlayer.Builder(requireContext())
            .setSeekBackIncrementMs(10000)
            .setSeekForwardIncrementMs(10000)
            .build()

        playerView.player = simpleExoPlayer
        playerView.keepScreenOn = true

        simpleExoPlayer.addListener(object : Player.Listener {
            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                if (playbackState == Player.STATE_BUFFERING) {
                    progressBar.visibility = View.VISIBLE
                    PlayerStatePublisher.notifyPlayerBuffering()
                } else if (playbackState == Player.STATE_READY) {
                    progressBar.visibility = View.GONE
                    PlayerStatePublisher.notifyPlayerReady()

                    if (playWhenReady) {
                        PlayerStatePublisher.notifyPlayerPlaying()
                    } else {
                        PlayerStatePublisher.notifyPlayerPaused()
                    }
                }
            }
        })


        val videoSource = Uri.parse(m3u8)
        val mediaItem = MediaItem.fromUri(videoSource)
        simpleExoPlayer.setMediaItem(mediaItem)
        simpleExoPlayer.prepare()
        simpleExoPlayer.play()
    }

    private fun setupFullscreenButton(m3u8: String?, title: String?) {
        val playerView = binding.player
        val btnFullscreen = playerView.findViewById<ImageView>(R.id.bt_fullscreen)
        btnFullscreen.setOnClickListener {

            val intent = Intent(requireContext(), WatchActivity::class.java).apply {
                putExtra("VIDEO_TITLE", title)
                putExtra("VIDEO_LINK", m3u8)
            }
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        simpleExoPlayer.playWhenReady = true
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
        Log.i("sehizopause", "pause")
        simpleExoPlayer.pause()
        PlayerStatePublisher.notifyPlayerPaused()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
