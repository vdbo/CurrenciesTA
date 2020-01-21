package com.vbta.currenciesta.presentation.utils

import androidx.recyclerview.widget.RecyclerView
import io.reactivex.Observer

enum class ScrollingState {
    IDLE, SCROLLING
}

class ScrollStateChangeListener(
    private val onStateChanged: Observer<ScrollingState>
) : RecyclerView.OnScrollListener() {

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        when (newState) {
            RecyclerView.SCROLL_STATE_IDLE -> onStateChanged.onNext(ScrollingState.IDLE)
            else -> onStateChanged.onNext(ScrollingState.SCROLLING)
        }
    }
}