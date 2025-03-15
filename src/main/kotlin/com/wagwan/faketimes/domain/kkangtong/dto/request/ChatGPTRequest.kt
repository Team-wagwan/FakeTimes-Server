package com.wagwan.faketimes.domain.kkangtong.dto.request

import com.wagwan.faketimes.domain.kkangtong.dto.Message

data class ChatGPTRequest(val model: String, private val prompt: String) {
    val messages: MutableList<Message> = ArrayList<Message>()

    init {
        messages.add(Message("user", prompt))
    }

}