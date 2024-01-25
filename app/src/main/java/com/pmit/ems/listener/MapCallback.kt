package com.pmit.pmitsudent.manager.listener

interface MapCallback<T, U> {
    fun onMapReceived(map: Map<T, U>)
}