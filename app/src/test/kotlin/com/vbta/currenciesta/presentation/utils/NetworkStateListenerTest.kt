package com.vbta.currenciesta.presentation.utils

import android.net.Network
import io.mockk.mockk
import io.reactivex.observers.TestObserver
import io.reactivex.subjects.PublishSubject
import org.junit.Test

class NetworkStateListenerTest {

    private val network = mockk<Network>()
    private val testObserver = TestObserver<NetworkState>()
    private val networkChanges = PublishSubject.create<NetworkState>()
    private val networkStateListener = NetworkStateListener(networkChanges)

    @Test
    fun `when onAvailable is triggered, observer is notified with an available state`() {
        networkChanges.subscribeWith(testObserver)

        networkStateListener.onAvailable(network)

        testObserver.assertNoErrors()
            .assertValues(NetworkState.Available)
    }

    @Test
    fun `when onLost is triggered, observer is notified with a lost state`() {
        networkChanges.subscribeWith(testObserver)

        networkStateListener.onLost(network)

        testObserver.assertNoErrors()
            .assertValues(NetworkState.Lost)
    }
}