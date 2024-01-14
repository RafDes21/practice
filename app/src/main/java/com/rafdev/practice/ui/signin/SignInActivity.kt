package com.rafdev.practice.ui.signin

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.rafdev.practice.R
import com.rafdev.practice.core.onTextChanged
import com.rafdev.practice.databinding.ActivitySignInBinding
import com.rafdev.practice.ui.fragment.third.ThirdFragment
import com.rafdev.practice.ui.login.LoginActivity
import com.rafdev.practice.ui.signin.model.UserSignIn
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInActivity : AppCompatActivity() {

    companion object {
        fun create(context: Context): Intent =
            Intent(context, SignInActivity::class.java)
    }

    private lateinit var binding: ActivitySignInBinding

    private val viewModel: SignInViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
    }

    private fun initUI() {
        initListeners()
        initObservers()
    }

    private fun initListeners() {
        binding.etRepeatPassword.onTextChanged { onFieldChanged() }
        binding.etPassword.onTextChanged { onFieldChanged() }
        binding.etRealName.onTextChanged { onFieldChanged() }
        binding.etNickname.onTextChanged { onFieldChanged() }
        binding.etEmail.onTextChanged { onFieldChanged() }

        binding.viewBottom.tvFooter.setOnClickListener { viewModel.onLoginSelected() }
        binding.viewHeader.ivLogo.setOnClickListener { finish() }

        with(binding) {
            btnCreateAccount.setOnClickListener {
                viewModel.onSignInSelected(
                    UserSignIn(
                        realName = binding.etRealName.text.toString(),
                        nickName = binding.etNickname.text.toString(),
                        email = binding.etEmail.text.toString(),
                        password = binding.etPassword.text.toString(),
                        passwordConfirmation = binding.etRepeatPassword.text.toString()
                    )
                )
            }
        }

    }

    private fun initObservers() {
        viewModel.navigateToLogin.observe(this) {
            it.getContentIfNotHandled()?.let {
                goToLogin()
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.viewState.collect { viewState ->
                updateUI(viewState)
            }
        }

    }

    private fun goToLogin() {
        startActivity(LoginActivity.create(this))
        finish()
    }

    private fun updateUI(viewState: SignInViewState) {
        with(binding) {
            pbLoading.isVisible = viewState.isLoading
            binding.tilEmail.error =
                if (viewState.isValidEmail) null else getString(R.string.signin_error_mail)
            binding.tilNickname.error =
                if (viewState.isValidNickName) null else getString(R.string.signin_error_nickname)
            binding.tilRealName.error =
                if (viewState.isValidRealName) null else getString(R.string.signin_error_realname)
            binding.tilPassword.error =
                if (viewState.isValidPassword) null else getString(R.string.signin_error_password)
            binding.tilRepeatPassword.error =
                if (viewState.isValidPassword) null else getString(R.string.signin_error_password)
        }
    }
    private fun onFieldChanged(hasFocus: Boolean = false) {
        if (!hasFocus) {
            viewModel.onFieldsChanged(
                UserSignIn(
                    realName = binding.etRealName.text.toString(),
                    nickName = binding.etNickname.text.toString(),
                    email = binding.etEmail.text.toString(),
                    password = binding.etPassword.text.toString(),
                    passwordConfirmation = binding.etRepeatPassword.text.toString()
                )
            )
        }
    }

}