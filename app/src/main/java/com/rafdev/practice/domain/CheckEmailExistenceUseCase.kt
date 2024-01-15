package com.rafdev.practice.domain

import com.rafdev.practice.data.network.AuthenticationService
import javax.inject.Inject

class CheckEmailExistenceUseCase @Inject constructor(
    private val authenticationService: AuthenticationService
) {

    suspend operator fun invoke(email: String): Boolean {
        return try {
            val result = authenticationService.isEmailTaken(email)

            return result?.signInMethods?.isNullOrEmpty() == false

        } catch (e: Exception) {
            // Manejar cualquier excepción que pueda ocurrir durante la verificación del correo electrónico
            false
        }

    }
}