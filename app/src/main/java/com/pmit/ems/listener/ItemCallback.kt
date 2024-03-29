package com.pmit.pmitsudent.manager.listener

interface ItemCallback<T> {
    fun onItem(item: T, vararg args: Any)

    fun onFailed() {}
}