package com.rafdev.practice.ui.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.rafdev.practice.databinding.ActivityLoginBinding
import com.rafdev.practice.ui.signin.SignInActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    companion object {
        fun create(context: Context): Intent =
            Intent(context, LoginActivity::class.java)
    }

    private lateinit var binding: ActivityLoginBinding

    private val viewModel: LoginViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
    }

    private fun initUI() {
        initListeners()
        initObservers()
    }

    private fun initListeners() {
        binding.viewBottom.setOnClickListener { viewModel.onSignInSelected() }
        binding.viewHeader.ivLogo.setOnClickListener{finish()}

    }

    private fun initObservers() {
        viewModel.navigateToSignIn.observe(this) {
            it.getContentIfNotHandled()?.let {
                goToLogin()
            }
        }
    }

    private fun goToLogin() {
        startActivity(SignInActivity.create(this))
        finish()

    }

}