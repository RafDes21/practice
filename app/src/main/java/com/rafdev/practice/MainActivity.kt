package com.rafdev.practice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.rafdev.practice.signin.SignIn

class MainActivity : AppCompatActivity() {

    private lateinit var navigation : BottomNavigationView

    private val onNavMenu = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {

            R.id.one -> {
                supportFragmentManager.commit {
                    replace(R.id.container_fragment, FirstFragment())
                    setReorderingAllowed(true)
                    addToBackStack("replacement")

                }
                return@OnNavigationItemSelectedListener true
            }
            R.id.two -> {
                supportFragmentManager.commit {
                    replace(R.id.container_fragment, SecondFragment())
                    setReorderingAllowed(true)
                    addToBackStack("replacement")

                }
                return@OnNavigationItemSelectedListener true
            }
            R.id.tree -> {
                supportFragmentManager.commit {
                    replace(R.id.container_fragment, ThirdFragment())
                    setReorderingAllowed(true)
                    addToBackStack("replacement")

                }
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigation = findViewById(R.id.nav_menu)
        navigation.setOnNavigationItemSelectedListener ( onNavMenu )

        supportFragmentManager.commit {
            replace(R.id.container_fragment, SignIn())
            setReorderingAllowed(true)
            addToBackStack("replacement")

        }
    }
}