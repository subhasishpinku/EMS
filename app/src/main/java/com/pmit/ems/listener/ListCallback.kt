package com.pmit.pmitsudent.manager.listener

interface ListCallback<T> {
    fun onMapReceived(items: List<T>)
}