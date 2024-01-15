package com.rafdev.practice.domain

import com.rafdev.practice.core.response.LoginResult
import com.rafdev.practice.data.network.AuthenticationService
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val authenticationService: AuthenticationService) {

    suspend operator fun invoke(email: String, password: String): LoginResult =
        authenticationService.login(email, password)
}