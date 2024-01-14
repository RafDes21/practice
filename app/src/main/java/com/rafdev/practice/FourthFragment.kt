package com.rafdev.practice

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FourthFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_fourth, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btn = view.findViewById<Button>(R.id.btnClick)
            btn.setOnClickListener {

                saveSelectedTab(5)

                parentFragmentManager.beginTransaction()
                    .replace(
                        R.id.profileContainerFragment,
                        FiveFragment()
                    )
                    .commit()
            }

    }

    private fun saveSelectedTab(tabNumber: Int) {
        val sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt(SecondFragment.SELECTED_TAB_KEY, tabNumber)
        editor.apply()
    }

}




