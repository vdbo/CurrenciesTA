package com.vbta.currenciesta.presentation.utils

import android.net.ConnectivityManager
import android.net.Network
import io.reactivex.Observer

sealed class NetworkState {
    object Available : NetworkState()

    object Lost : NetworkState() {
        val error = RuntimeException("Internet connection was lost")
    }
}

class NetworkStateListener(
    private val networkStateChanges: Observer<NetworkState>
) : ConnectivityManager.NetworkCallback() {

    override fun onAvailable(network: Network) {
        networkStateChanges.onNext(NetworkState.Available)
    }

    override fun onLost(network: Network) {
        networkStateChanges.onNext(NetworkState.Lost)
    }

}
