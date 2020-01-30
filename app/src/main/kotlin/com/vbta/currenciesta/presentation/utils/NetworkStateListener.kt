package com.vbta.currenciesta.presentation.utils

import android.net.ConnectivityManager
import android.net.Network
import io.reactivex.Observer

enum class NetworkState {
    AVAILABLE, LOST
}

class NetworkStateListener(
    private val networkObserver: Observer<NetworkState>
) : ConnectivityManager.NetworkCallback() {

    override fun onAvailable(network: Network) {
        super.onAvailable(network)
        networkObserver.onNext(NetworkState.AVAILABLE)
    }

    override fun onLost(network: Network) {
        super.onLost(network)
        networkObserver.onNext(NetworkState.LOST)
    }

}
