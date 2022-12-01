package com.example.hrautomation.utils.ui.switcher.base

import android.view.View
import com.example.hrautomation.utils.ui.switcher.base.processor.StateViewProcessor
import java.util.*

abstract class StateQueueManager<State : StateQueueManager.ContentState> {

    protected val stateQueue: LinkedList<ContentStateNode<State>> = LinkedList()

    var isAnimating: Boolean = false

    protected var settings: Settings<State>? = null

    protected abstract val currentStateNode: ContentStateNode<State>

    protected abstract val nextStateNode: ContentStateNode<State>?

    abstract fun setup(settings: Settings<State>)

    open fun onPostStateSwitched() {}

    open fun onPostStateStarted() {}

    open fun onPostStateCancelled(mode: StateViewProcessor.CancelAnimationMode) {}

    protected fun launchQueue() {
        if (!isAnimating && hasMoreStates()) {
            val nextState = nextStateNode ?: return
            val settings = requireNotNull(settings) { "State switcher settings must be set" }

            val fromViews = settings.getViewsFromState(currentStateNode.state)
            val toViews = settings.getViewsFromState(nextState.state)

            nextState.processor.processStateTransition(fromViews, toViews, nextState.animationParams, switchStateListenerImpl)
        }
    }

    protected abstract fun prepareNextIteration()

    protected val switchStateListenerImpl = object : StateViewProcessor.SwitchStateListener {

        override fun onStateSwitched() {
            isAnimating = false
            prepareNextIteration()
            onPostStateSwitched()
            launchQueue()
        }

        override fun onSwitchingCanceled(mode: StateViewProcessor.CancelAnimationMode) {
            isAnimating = false
            onPostStateCancelled(mode)
        }

        override fun onSwitchingStarted() {
            isAnimating = true
            onPostStateStarted()
        }
    }

    interface ContentState

    interface Settings<State : ContentState> {

        val initState: State

        fun setup()

        fun getViewsFromState(state: State): List<View>
    }

    protected fun hasMoreStates(): Boolean {
        return stateQueue.size > 1
    }

    protected class ContentStateNode<State : ContentState>(
        val state: State,
        val animationParams: SwitchAnimationParams?,
        val processor: StateViewProcessor
    )
}

