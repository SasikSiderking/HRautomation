package com.example.hrautomation.utils.ui.switcher.base

abstract class StateSwitcher<
        State : StateQueueManager.ContentState,
        QueueManager : StateQueueManager<State>
        > {
    abstract val stateQueueManager: QueueManager

    protected open fun onPostSetup(settings: StateQueueManager.Settings<State>) {}

    fun setup(settings: StateQueueManager.Settings<State>) {
        stateQueueManager.setup(settings)
        onPostSetup(settings)
    }
}