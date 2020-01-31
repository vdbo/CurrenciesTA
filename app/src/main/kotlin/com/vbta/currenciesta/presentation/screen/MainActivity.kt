package com.vbta.currenciesta.presentation.screen

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkRequest
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.vbta.currenciesta.R
import com.vbta.currenciesta.presentation.screen.currencies.CurrenciesFragment
import com.vbta.currenciesta.presentation.utils.NetworkStateListener
import kotlinx.android.synthetic.main.main_activity.*
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private val networkStateListener: NetworkStateListener by inject()
    private val connectivityManager by lazy(LazyThreadSafetyMode.NONE) {
        (getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
    }

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

    override fun onStart() {
        super.onStart()
        connectivityManager.registerNetworkCallback(NetworkRequest.Builder().build(), networkStateListener)
    }

    override fun onStop() {
        super.onStop()
        connectivityManager.unregisterNetworkCallback(networkStateListener)
    }

}
