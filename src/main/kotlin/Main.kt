package ru.netology

fun main() {

    val service = ChatService()
    val userId = 456

    service.addMessage(listOf(userId, 123), Message(text = "message1"))
    service.addMessage(listOf(userId, 123), Message(text = "message4"))
    service.addMessage(listOf(userId, 111), Message(text = "message2"))
    service.addMessage(listOf(userId, 111), Message(text = "message5"))
    service.addMessage(listOf(userId, 111), Message(text = "message6"))
    service.addMessage(listOf(122, 455), Message(text = "message3"))
    println(service)

    println("все чаты пользователя $userId: " + service.getChats(userId).toString())
    println("все чаты пользователя 789: " + service.getChats(789).toString())

    var chat = service.getChat(listOf(111, userId))
    println("чат пользователя $userId с пользователем 111" + chat)

    chat = service.getChat(listOf(117, userId))
    println("чат пользователя $userId с пользователем 117" + chat)

    println("получить список сообщений пользователя $userId с пользователем 123 и прочитать их:")
    println(service.getMessagesFromChat())
    println(service)
    println("Количество чатов с непрочитанными сообщениями пользователя $userId " + service.getUnreadChatsCount(userId))
    println("Прочитать все сообщения пользователя $userId и 111")
    service.readChat(listOf(111, userId))
    println(service)
    println("Количество чатов с непрочитанными сообщениями пользователя $userId " + service.getUnreadChatsCount(userId))
    println("Добавим новое сообщение для пользователя $userId и 111")
    service.addMessage(listOf(userId, 111), Message(text = "message7"))
    println(service)
    println("Количество чатов с непрочитанными сообщениями пользователя $userId " + service.getUnreadChatsCount(userId))
    println("удаления чата пользователя 111 и $userId")
    if (service.deleteChat(listOf(456, 111))) println("чат удален") else println("чат отсутствует")
    println(service)
    println("удаления чата пользователя 111 и $userId")
    if (service.deleteChat(listOf(456, 111))) println("чат удален") else println("чат отсутствует")
    println(service)
}

private fun ChatService.getMessagesFromChat() {
    println("нет сообщений")
}
