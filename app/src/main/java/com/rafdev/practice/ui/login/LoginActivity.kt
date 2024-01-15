package com.rafdev.practice.ui.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
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

        binding.btnLogin.setOnClickListener {
            viewModel.onLoginSelected(
                binding.etEmail.text.toString(),
                binding.etPassword.text.toString()
            )
        }

    }

    private fun initObservers() {
        viewModel.navigateToSignIn.observe(this) {
            it.getContentIfNotHandled()?.let {
                goToLogin()
            }
        }

        viewModel.navigateToDetails.observe(this) {
            it.getContentIfNotHandled()?.let {
                goToDetail()
            }
        }
        viewModel.navigateToVerifyAccount.observe(this) {
            it.getContentIfNotHandled()?.let {
                goToVerify()
            }
        }
        viewModel.showContinueButton.observe(this) {
            it.getContentIfNotHandled()?.let {
                goToVe()
            }
        }
    }

    private fun goToVe() {
        binding.progress.visibility = View.GONE
    }

    private fun goToLogin() {
        startActivity(SignInActivity.create(this))
        finish()

    }
    private fun goToDetail() {
        finish()
        binding.progress.visibility = View.GONE
    }

    private fun goToVerify() {
        Log.i("usuario", "verificando")
        binding.progress.visibility = View.VISIBLE
    }

}