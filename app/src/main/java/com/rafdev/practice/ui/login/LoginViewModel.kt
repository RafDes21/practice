package com.rafdev.practice.ui.login

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rafdev.practice.core.Event
import com.rafdev.practice.core.response.LoginResult
import com.rafdev.practice.domain.LoginUseCase
import com.rafdev.practice.domain.SendEmailVerificationUseCase
import com.rafdev.practice.domain.VerifyEmailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    val loginUseCase: LoginUseCase,
    val sendEmailVerificationUseCase: SendEmailVerificationUseCase,
    val verificationUseCase: VerifyEmailUseCase
) : ViewModel() {

    private companion object {
        const val MIN_PASSWORD_LENGTH = 6
    }

    //
    private val _navigateToDetails = MutableLiveData<Event<Boolean>>()
    val navigateToDetails: LiveData<Event<Boolean>>
        get() = _navigateToDetails

    private val _showContinueButton = MutableLiveData<Event<Boolean>>()
    val showContinueButton: LiveData<Event<Boolean>>
        get() = _showContinueButton
    //    private val _navigateToForgotPassword = MutableLiveData<Event<Boolean>>()
//    val navigateToForgotPassword: LiveData<Event<Boolean>>
//        get() = _navigateToForgotPassword
//
    private val _navigateToSignIn = MutableLiveData<Event<Boolean>>()
    val navigateToSignIn: LiveData<Event<Boolean>>
        get() = _navigateToSignIn

    //
    private val _navigateToVerifyAccount = MutableLiveData<Event<Boolean>>()
    val navigateToVerifyAccount: LiveData<Event<Boolean>>
        get() = _navigateToVerifyAccount

    //
    private val _viewState = MutableStateFlow(LoginViewState())
    val viewState: StateFlow<LoginViewState>
        get() = _viewState

    //
//    private var _showErrorDialog = MutableLiveData(UserLogin())
//    val showErrorDialog: LiveData<UserLogin>
//        get() = _showErrorDialog
//
    fun onLoginSelected(email: String, password: String) {
        if (isValidEmail(email) && isValidPassword(password)) {
            loginUser(email, password)
        } else {
            onFieldsChanged(email, password)
        }
    }

    private fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            _viewState.value = LoginViewState(isLoading = true)
            sendEmailVerificationUseCase()
            verificationUseCase().collect { isVerified ->
                if (isVerified) {
                    // El correo ha sido verificado, puedes realizar acciones aquí
                    Log.i("usuario", "Correo verificado")
                    // Por ejemplo, puedes navegar a la siguiente pantalla
                    _navigateToDetails.value = Event(true)
                }
            }//             if (ver){
//                 _showContinueButton.value = Event(true)
//
//             }
//            val result = loginUseCase(email, password)
//
//            Log.i("usuario", "res $result")

            when (val result = loginUseCase(email, password)) {
                LoginResult.Error -> {
//                    _showErrorDialog.value =
//                        UserLogin(email = email, password = password, showErrorDialog = true)
//                    _viewState.value = LoginViewState(isLoading = false)
                }

                is LoginResult.Success -> {
                    if (result.verified) {
                        Log.i("usuario", "verificado")
                        _navigateToDetails.value = Event(true)
                    } else {
                        Log.i("usuario", "noverificado")

                        _navigateToVerifyAccount.value = Event(true)
                    }
                }

                else -> {}
            }
            _viewState.value = LoginViewState(isLoading = false)
        }
    }

    fun onFieldsChanged(email: String, password: String) {
        _viewState.value = LoginViewState(
            isValidEmail = isValidEmail(email),
            isValidPassword = isValidPassword(password)
        )
    }

    //    fun onForgotPasswordSelected() {
//        _navigateToForgotPassword.value = Event(true)
//    }
//
    fun onSignInSelected() {
        _navigateToSignIn.value = Event(true)
    }

    private fun isValidEmail(email: String) =
        Patterns.EMAIL_ADDRESS.matcher(email).matches() || email.isEmpty()

    private fun isValidPassword(password: String): Boolean =
        password.length >= MIN_PASSWORD_LENGTH || password.isEmpty()

}
