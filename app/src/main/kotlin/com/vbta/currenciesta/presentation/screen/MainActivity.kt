package com.vbta.currenciesta.presentation.screen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.vbta.currenciesta.R
import com.vbta.currenciesta.presentation.screen.rates.CurrenciesFragment
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        setSupportActionBar(toolbar)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, CurrenciesFragment.newInstance())
                .commitNow()
        }
    }

}
