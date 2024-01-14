package com.rafdev.practice.ui.fragment.third

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.rafdev.practice.databinding.FragmentThirdBinding
import com.rafdev.practice.ui.login.LoginActivity
import com.rafdev.practice.ui.signin.SignInActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ThirdFragment : Fragment() {

    private var _binding: FragmentThirdBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ThirdViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentThirdBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        initListeners()
        initObservers()
    }

    private fun initListeners() {
        with(binding) {
            btnLogin.setOnClickListener { viewModel.onLoginSelected() }
            btnRegister.setOnClickListener { viewModel.onSignInSelected() }
        }
    }


    private fun initObservers() {
        viewModel.navigateToLogin.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let {
                goToLogin()
            }
        }
        viewModel.navigateToSignIn.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let {
                goToSingIn()
            }
        }
    }


    private fun goToSingIn() {
        startActivity(SignInActivity.create(requireContext()))
    }

    private fun goToLogin() {
        startActivity(LoginActivity.create(requireContext()))
    }

}