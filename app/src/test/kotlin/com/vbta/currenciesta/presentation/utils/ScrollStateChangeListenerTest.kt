package com.vbta.currenciesta.presentation.utils

import androidx.recyclerview.widget.RecyclerView
import io.mockk.mockk
import io.reactivex.observers.TestObserver
import io.reactivex.subjects.PublishSubject
import org.junit.Test

class ScrollStateChangeListenerTest {

    private val testObserver = TestObserver<ScrollingState>()
    private val scrollStateChanges = PublishSubject.create<ScrollingState>()
    private val scrollStateChangeListener = ScrollStateChangeListener(scrollStateChanges)

    @Test
    fun `when onScrollStateChanged called with idle state should notify observer with IDLE state`() {
        scrollStateChanges.subscribeWith(testObserver)

        scrollStateChangeListener.onScrollStateChanged(mockk(), RecyclerView.SCROLL_STATE_IDLE)

        testObserver.assertNoErrors()
            .assertValue(ScrollingState.IDLE)
    }

    @Test
    fun `when onScrollStateChanged called with settling state, should notify observer with SCROLLING state`() {
        scrollStateChanges.subscribeWith(testObserver)

        scrollStateChangeListener.onScrollStateChanged(mockk(), RecyclerView.SCROLL_STATE_SETTLING)

        testObserver.assertNoErrors()
            .assertValue(ScrollingState.SCROLLING)
    }

    @Test
    fun `when onScrollStateChanged called with dragging state, should notify observer with SCROLLING state`() {
        scrollStateChanges.subscribeWith(testObserver)

        scrollStateChangeListener.onScrollStateChanged(mockk(), RecyclerView.SCROLL_STATE_DRAGGING)

        testObserver.assertNoErrors()
            .assertValue(ScrollingState.SCROLLING)
    }
}