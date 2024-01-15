package com.rafdev.practice.data.network

import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import com.rafdev.practice.ui.signin.model.UserSignIn

class UserService @Inject constructor(private val firebase: FirebaseClient) {

    companion object {
        const val USER_COLLECTION = "users"
    }

    suspend fun createUserTable(userSignIn: UserSignIn, userId: String?) = runCatching {

        val user = hashMapOf(
            "userId" to userId,
            "email" to userSignIn.email,
            "nickname" to userSignIn.nickName,
            "realname" to userSignIn.realName
        )

        firebase.db
            .collection(USER_COLLECTION)
            .add(user).await()
            .set(user).await()

    }.isSuccess
}