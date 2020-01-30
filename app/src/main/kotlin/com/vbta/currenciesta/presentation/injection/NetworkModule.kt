package com.vbta.currenciesta.presentation.injection

import com.vbta.currenciesta.presentation.utils.NetworkState
import com.vbta.currenciesta.presentation.utils.NetworkStateListener
import io.reactivex.Observer
import io.reactivex.subjects.PublishSubject

fun provideNetworkChangesSubject() = PublishSubject.create<NetworkState>()

fun provideNetworkStateListener(
    networkChangesObserver: Observer<NetworkState>
) = NetworkStateListener(networkChangesObserver)