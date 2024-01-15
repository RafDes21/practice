package com.rafdev.practice.domain

import com.rafdev.practice.data.network.AuthenticationService
import javax.inject.Inject

class SendEmailVerificationUseCase @Inject constructor(private val authenticationService: AuthenticationService) {

    suspend operator fun invoke() = authenticationService.sendVerificationEmail()
}