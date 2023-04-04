package com.example.chatapp.util

sealed class Resource<out T> {
    data class Success<out T>(val result: T): Resource<T>()
    data class Error(val message: String): Resource<Nothing>()
    object Loading: Resource<Nothing>()
}