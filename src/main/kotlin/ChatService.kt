package ru.netology
import java.util.*

class ChatService {
    val chats = hashMapOf<SortedSet<Int>, Chat>()

    override fun toString(): String {
        var result: String = "Состояние чатов:\n"
        chats.onEach { (key, value) ->
            result += "$key $value \n"
        }
        return result
    }

    fun addMessage(userIds: List<Int>, message: Message): Chat {
        return chats.getOrPut(userIds.toSortedSet()) {
            Chat(null)
        }.apply {
            messages.add(message)
        }
    }

    fun getMessagesFromChat(userIds: SortedSet<Int>): List<Message> {
        val chat = chats.filter { entry -> entry.key.containsAll(userIds) }.values.first().messages
        chat.onEach { message -> message.isRead = true }
        return chat
    }

    fun readChat(userIds: List<Int>): Boolean {
        chats.filter { entry -> entry.key.containsAll(userIds) }.values.first().readMessages()
        return true
    }

    fun deleteChat(userIds: List<Int>): Boolean {
        return chats.remove(userIds.toSortedSet()) != null
    }

    fun getChats(userId: Int): List<Chat> {
        return chats.filter { entry -> entry.key.contains(userId) }.values.toList()
    }

    fun getChat(userIds: List<Int>): List<Chat> {
        return chats.filter { entry -> entry.key.containsAll(userIds) }.values.toList()
    }

    fun getUnreadChatsCount(userId: Int): Int {
        return chats.filter { entry -> entry.key.contains(userId) }.values.filter { !it.unreadMessagesB() }.count()
    }

    fun getLastMessages(chatIds: List<SortedSet<Int>>): List<String> {
        return chatIds.flatMap { chatId ->
            val MESSAGE_LIMIT = 10
            getMessagesFromChat(chatId)
                .reversed()
                .take(MESSAGE_LIMIT)
                .map { message ->
                    message.text
                }.filterNotNull()
        }
    }

    fun readMessagesFromChatById(userId: Int, messageCount: Int) {
        chats
            .filter { it.value.userId == userId }
            .onEach { chat ->
                getMessagesFromChat(chat.key)
                    .take(messageCount)
                    .onEach {
                        it.isRead = true
                    }
            }
    }
}