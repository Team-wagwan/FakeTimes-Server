package com.wagwan.faketimes.domain.kkangtong.dto.response

import com.wagwan.faketimes.domain.kkangtong.dto.Message

data class ChatGPTResponse (val choices: List<Choice>? = null){

    data class Choice( val index:Int = 0, val message: Message? = null)

}