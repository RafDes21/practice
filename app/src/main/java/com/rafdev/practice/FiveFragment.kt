package com.rafdev.practice

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button


class FiveFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_five, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btn = view.findViewById<Button>(R.id.btnClick)
        btn.setOnClickListener {

            saveSelectedTab(4)

            parentFragmentManager.beginTransaction()
                .replace(
                    R.id.profileContainerFragment,
                    FourthFragment()
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