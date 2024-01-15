package com.rafdev.practice.domain

import android.util.Log
import com.rafdev.practice.data.network.AuthenticationService
import com.rafdev.practice.data.network.UserService
import com.rafdev.practice.ui.signin.model.UserSignIn
import javax.inject.Inject

class CreateAccountUseCase @Inject constructor(
    private val authenticationService: AuthenticationService,
    private val userService: UserService
) {

    suspend operator fun invoke(userSignIn: UserSignIn): Boolean {
//        val accountCreated =
        val authResult = authenticationService.createAccount(userSignIn.email, userSignIn.password)
        return if (authResult != null) {
            val uid = authResult.user?.uid

            // Loguear el UID (opcional)
            Log.i("UID", "UID: $uid")
            userService.createUserTable(userSignIn, userId = uid)
        } else {
            false
        }
    }
}