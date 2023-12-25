package ru.netology

data class Message(
    var isRead: Boolean = false,
    val text: String,
)