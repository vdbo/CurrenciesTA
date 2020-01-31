package com.vbta.currenciesta.presentation.utils

import androidx.recyclerview.widget.RecyclerView
import io.reactivex.Observer

enum class ScrollingState {
    IDLE, SCROLLING
}

class ScrollStateChangeListener(
    private val scrollStateChanges: Observer<ScrollingState>
) : RecyclerView.OnScrollListener() {

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        when (newState) {
            RecyclerView.SCROLL_STATE_IDLE -> scrollStateChanges.onNext(ScrollingState.IDLE)
            else -> scrollStateChanges.onNext(ScrollingState.SCROLLING)
        }
    }
}