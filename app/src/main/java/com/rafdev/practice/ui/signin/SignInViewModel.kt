package com.rafdev.practice.ui.signin

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rafdev.practice.core.Event
import com.rafdev.practice.domain.CheckEmailExistenceUseCase
import com.rafdev.practice.domain.CreateAccountUseCase
import com.rafdev.practice.ui.signin.model.UserSignIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    val createAccountUseCase: CreateAccountUseCase,
    val checkEmailExistenceUseCase: CheckEmailExistenceUseCase
) : ViewModel() {

    private companion object {
        const val MIN_PASSWORD_LENGTH = 6
    }


    private val _navigateToLogin = MutableLiveData<Event<Boolean>>()
    val navigateToLogin: LiveData<Event<Boolean>>
        get() = _navigateToLogin

    private val _viewState = MutableStateFlow(SignInViewState())
    val viewState: StateFlow<SignInViewState>
        get() = _viewState

    fun onLoginSelected() {
        _navigateToLogin.value = Event(true)
    }

    fun onSignInSelected(userSignIn: UserSignIn) {
        val viewState = userSignIn.toSignInViewState()
        if (viewState.userValidated() && userSignIn.isNotEmpty()) {
            signInUser(userSignIn)
        } else {
            onFieldsChanged(userSignIn)
        }
    }


    private fun signInUser(userSignIn: UserSignIn) {
        viewModelScope.launch {
            try {
                _viewState.value = SignInViewState(isLoading = true)
                val isEmailTaken = checkEmailExistenceUseCase(userSignIn.email)
                Log.i("usuario", "isEmailTaken: $isEmailTaken")

                if (isEmailTaken) {
                    Log.i("usuario", "usuario ya existe")
                } else {
                    val accountCreated = createAccountUseCase(userSignIn)
                    if (accountCreated) {
                        Log.i("usuario", "creado")
                        // _navigateToVerifyEmail.value = Event(true)
                    } else {
                        Log.i("usuario", "error al crear la cuenta")
                        // _showErrorDialog.value = true
                    }
                }
            } catch (e: Exception) {
                Log.e("SignInViewModel", "Error en signInUser", e)
            } finally {
                _viewState.value = SignInViewState(isLoading = false)
            }
        }
    }


    fun onFieldsChanged(userSignIn: UserSignIn) {
        _viewState.value = userSignIn.toSignInViewState()
    }

    private fun isValidOrEmptyEmail(email: String) =
        Patterns.EMAIL_ADDRESS.matcher(email).matches() || email.isEmpty()

    private fun isValidOrEmptyPassword(password: String, passwordConfirmation: String): Boolean =
        (password.length >= MIN_PASSWORD_LENGTH && password == passwordConfirmation) || password.isEmpty() || passwordConfirmation.isEmpty()

    private fun isValidName(name: String): Boolean =
        name.length >= MIN_PASSWORD_LENGTH || name.isEmpty()

    private fun UserSignIn.toSignInViewState(): SignInViewState {
        return SignInViewState(
            isValidEmail = isValidOrEmptyEmail(email),
            isValidPassword = isValidOrEmptyPassword(password, passwordConfirmation),
            isValidNickName = isValidName(nickName),
            isValidRealName = isValidName(realName)
        )
    }


}