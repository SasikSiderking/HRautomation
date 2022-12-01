package com.example.hrautomation.utils.ui.switcher

import com.example.hrautomation.utils.ui.switcher.base.SimpleStateQueueManager
import com.example.hrautomation.utils.ui.switcher.base.StateSwitcher
import com.example.hrautomation.utils.ui.switcher.base.SwitchAnimationParams
import com.example.hrautomation.utils.ui.switcher.base.processor.SetStateViewProcessor
import com.example.hrautomation.utils.ui.switcher.base.processor.StateViewProcessor
import com.example.hrautomation.utils.ui.switcher.base.processor.SwitchStateViewProcessor

class ContentLoadingStateSwitcher : StateSwitcher<ContentLoadingState, SimpleStateQueueManager<ContentLoadingState>>() {

    override val stateQueueManager: SimpleStateQueueManager<ContentLoadingState> = SimpleStateQueueManager()

    val currentState: ContentLoadingState
        get() = stateQueueManager.currentState

    fun setState(
        state: ContentLoadingState,
        animationParams: SwitchAnimationParams? = SwitchAnimationParams()
    ) {
        stateQueueManager.setState(state, animationParams, SetStateViewProcessor())
    }

    fun addStateToQueue(
        state: ContentLoadingState,
        animationParams: SwitchAnimationParams? = SwitchAnimationParams()
    ) {
        stateQueueManager.addStateToQueue(state, animationParams, SwitchStateViewProcessor())
    }

    fun switchState(
        state: ContentLoadingState,
        animationParams: SwitchAnimationParams? = SwitchAnimationParams()
    ) {
        stateQueueManager.switchState(state, animationParams, SwitchStateViewProcessor())
    }

    fun cancelAnimation(
        cancelMode: StateViewProcessor.CancelAnimationMode = StateViewProcessor.CancelAnimationMode.BACKWARD
    ) {
        stateQueueManager.cancelAnimation(cancelMode)
    }
}