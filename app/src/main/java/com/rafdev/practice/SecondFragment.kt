package com.rafdev.practice

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.commit

class SecondFragment : Fragment() {

    companion object {
        const val SELECTED_TAB_KEY = "SELECTED_TAB_KEY"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
            childFragmentManager.beginTransaction()
                .replace(R.id.profileContainerFragment, RegisterFragment())
                .commit()
        // Recuperar el estado actual desde SharedPreferences
//        val selectedTab = loadSelectedTab()

//        if (savedInstanceState == null) {
//            // Solo realizar la transacción inicial si no hay un estado guardado (evitar duplicados)
//            childFragmentManager.beginTransaction()
//                .replace(
//                    R.id.profileContainerFragment,
//                    when (selectedTab) {
//                        4 -> FourthFragment()
//                        5 -> FiveFragment()
//                        6 -> SixFragment()
//                        else -> FourthFragment()  // Fragment por defecto en caso de que no se haya guardado ningún estado
//                    }
//                )
//                .commit()
//        }
    }

//    private fun loadSelectedTab(): Int {
//        val sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE)
//        return sharedPreferences.getInt(
//            SELECTED_TAB_KEY,
//            4
//        ) // Valor por defecto es 4 si no hay nada guardado
//    }
}
