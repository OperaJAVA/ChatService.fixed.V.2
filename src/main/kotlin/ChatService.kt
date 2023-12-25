package ru.netology

// Класс, представляющий сервис работы с чатами
class ChatService {
    val chats = hashMapOf<Set<Int>, Chat>()

    override fun toString(): String {
        var result = "Состояние чатов:\n"
        chats.forEach { (key, value) ->
            result += "$key $value \n"
        }
        return result
    }

    // Метод для добавления сообщения в чат
    fun addMessage(userIds: List<Int>, message: Message): Chat {
        return chats.getOrPut(userIds.toSet()) {
            Chat(null)
        }.apply {
            messages.add(message)
        }
    }

    // Метод для получения сообщений из чата по идентификатору пользователей
    fun getMessagesFromChat(userIds: Set<Int>): List<Message> {
        val chat = chats.filter { entry -> entry.key.containsAll(userIds) }.values.firstOrNull()
        chat?.messages?.forEach { message -> message.isRead = true }
        return chat?.messages ?: emptyList()
    }

    // Метод для пометки сообщений в чате как прочитанных
    fun readChat(userIds: List<Int>): Boolean {
        val chat = chats.filter { entry -> entry.key.containsAll(userIds) }.values.firstOrNull()
        chat?.readMessages()
        return true
    }

    // Метод для удаления чата по идентификатору пользователей
    fun deleteChat(userIds: List<Int>): Boolean {
        return chats.remove(userIds.toSet()) != null
    }

    // Метод для получения всех чатов пользователя
    fun getChats(userId: Int): List<Chat> {
        return chats.filter { entry -> entry.key.contains(userId) }.values.toList()
    }

    // Метод для получения чата по идентификатору пользователей
    fun getChat(userIds: List<Int>): List<Chat> {
        return chats.filter { entry -> entry.key.containsAll(userIds) }.values.toList()
    }

    // Метод для получения количества непрочитанных чатов у пользователя
    fun getUnreadChatsCount(userId: Int): Int {
        return chats.filter { entry -> entry.key.contains(userId) }.values.filter { !it.hasUnreadMessages() }.count()
    }

    // Метод для получения последних сообщений из чатов
    fun getLastMessages(chatIds: List<Set<Int>>): List<String> {
        return chatIds.flatMap { chatId ->
            val messageLimit = 10
            getMessagesFromChat(chatId)
                .reversed()
                .take(messageLimit)
                .mapNotNull { message ->
                    message.text
                }
        }
    }

    // Метод для пометки сообщений в чате как прочитанных по идентификатору пользователя и количеству сообщений
    fun readMessagesFromChatById(userId: Int, messageCount: Int) {
        chats
            .filter { it.value.userId == userId }
            .forEach { chat ->
                getMessagesFromChat(chat.key)
                    .take(messageCount)
                    .forEach {
                        it.isRead = true
                    }
            }
    }
}