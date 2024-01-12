package com.rafdev.practice

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.rafdev.practice.databinding.FragmentRegisterBinding


class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? =  null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val generoOptions = resources.getStringArray(R.array.gender_options)
//        Log.i("GeneroOptions", generoOptions.joinToString(", "))
        val generoOptions = arrayOf("Masculino", "Femenino", "Otro")

        val generoAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, generoOptions)
        binding.actAutocomplete.setAdapter(generoAdapter)
        Log.i("GeneroAdapter", "Número de elementos en el adaptador: ${generoAdapter.count}")


        binding.actAutocomplete.setAdapter(generoAdapter)

        binding.actAutocomplete.setOnItemClickListener { _, _, position, _ ->
            val selectedGenero = generoAdapter.getItem(position)
            showToast("Género seleccionado: $selectedGenero")
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}
