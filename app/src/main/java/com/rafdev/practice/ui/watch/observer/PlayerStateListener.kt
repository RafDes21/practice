package com.digitalproserver.android_latina.ui.watch.observer

interface PlayerStateListener {
    fun onPlayerBuffering()
    fun onPlayerReady()
    fun onPlayerPaused()
    fun onPlayerPlay()
}