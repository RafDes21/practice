package com.rafdev.practice

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.rafdev.practice.core.dismissKeyboard
import com.rafdev.practice.databinding.FragmentLoginBinding
import com.rafdev.practice.ui.signin.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListeners()

    }

    private fun initListeners() {
//        binding.etEmail.loseFocusAfterAction(EditorInfo.IME_ACTION_NEXT)
//        binding.etEmail.onTextChanged { onFieldChanged() }
//        binding.etPassword.loseFocusAfterAction(EditorInfo.IME_ACTION_DONE)
//        binding.etPassword.setOnFocusChangeListener { _, hasFocus -> onFieldChanged(hasFocus) }
        binding.btnLogin.setOnClickListener {
            it.dismissKeyboard()

        }

    }
}