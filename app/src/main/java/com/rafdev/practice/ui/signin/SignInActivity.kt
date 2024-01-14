package com.rafdev.practice.ui.signin

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.rafdev.practice.databinding.ActivitySignInBinding
import com.rafdev.practice.ui.fragment.third.ThirdFragment
import com.rafdev.practice.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInActivity : AppCompatActivity() {

    companion object {
        fun create(context: Context): Intent =
            Intent(context, SignInActivity::class.java)
    }

    private lateinit var binding: ActivitySignInBinding

    private val viewModel:SignInViewModel by viewModels()
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
        binding.viewBottom.tvFooter.setOnClickListener {viewModel.onLoginSelected() }
        binding.viewHeader.ivLogo.setOnClickListener{finish()}
    }
    private fun initObservers() {
        viewModel.navigateToLogin.observe(this) {
            it.getContentIfNotHandled()?.let {
                goToLogin()
            }
        }
    }

    private fun goToLogin() {
        startActivity(LoginActivity.create(this))
        finish()
    }
}