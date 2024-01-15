package com.rafdev.practice.domain

import android.util.Log
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.rafdev.practice.data.network.AuthenticationService
import com.rafdev.practice.data.network.UserService
import com.rafdev.practice.ui.signin.model.UserSignIn
import javax.inject.Inject

class CreateAccountUseCase @Inject constructor(
    private val authenticationService: AuthenticationService,
    private val userService: UserService
) {

    suspend operator fun invoke(userSignIn: UserSignIn): Boolean {
        return try {
            val accountCreated = authenticationService.createAccount(userSignIn.email, userSignIn.password)
            Log.i("usuario", "uid $accountCreated")

            if (accountCreated != null) {
                val uid = accountCreated?.user?.uid
                Log.i("usuario", "uid $uid")
                userService.createUserTable(userSignIn, userId = uid)
            } else {
                // El registro fue exitoso
                false
            }
        } catch (e: FirebaseAuthUserCollisionException) {
            // El correo electrónico ya está registrado
            Log.i("CreateAccountUseCase", "Correo electrónico ya registrado")
            false
        } catch (e: Exception) {
            // Otros errores durante el registro
            false
        }
    }
}