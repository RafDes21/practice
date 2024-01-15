package com.rafdev.practice.domain

import android.util.Log
import com.rafdev.practice.data.network.AuthenticationService
import javax.inject.Inject

class CheckEmailExistenceUseCase @Inject constructor(
    private val authenticationService: AuthenticationService
) {

    suspend operator fun invoke(email: String): Boolean {
        return try {
            val result = authenticationService.isEmailTaken(email)
            Log.i("CheckEmailExistenceUseCase", "Result: $result")
            result?.signInMethods.isNullOrEmpty()
        } catch (e: Exception) {
            // Manejar cualquier excepción que pueda ocurrir durante la verificación del correo electrónico
            Log.e("CheckEmailExistenceUseCase", "Error en isEmailTaken", e)
            false
        }

    }
}