package com.digitalproserver.android_latina.ui.watch.observer

object PlayerStatePublisher {
    private val listeners = mutableSetOf<PlayerStateListener>()

    fun registerListener(listener: PlayerStateListener) {
        listeners.add(listener)
    }

    fun unregisterListener(listener: PlayerStateListener) {
        listeners.remove(listener)
    }
    fun notifyPlayerPlaying(){
        listeners.forEach { it.onPlayerPlay() }

    }
    fun notifyPlayerPaused() {
        listeners.forEach { it.onPlayerPaused() }
    }
    fun notifyPlayerBuffering() {
        listeners.forEach { it.onPlayerBuffering() }
    }

    fun notifyPlayerReady() {
        listeners.forEach { it.onPlayerReady() }
    }
}