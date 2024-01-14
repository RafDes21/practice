package com.rafdev.practice.ui.fragment.third

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rafdev.practice.core.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ThirdViewModel @Inject constructor() : ViewModel() {

    private val _navigateToLogin = MutableLiveData<Event<Boolean>>()
    val navigateToLogin: LiveData<Event<Boolean>>
        get() = _navigateToLogin

    private val _navigateToSignIn = MutableLiveData<Event<Boolean>>()
    val navigateToSignIn: LiveData<Event<Boolean>>
        get() = _navigateToSignIn

    fun onLoginSelected() {
        _navigateToLogin.value = Event(true)
    }

    fun onSignInSelected() {
        _navigateToSignIn.value = Event(true)
    }
}