package com.wagwan.faketimes.domain.kkangtong.dto

import com.wagwan.faketimes.domain.kkangtong.domain.entity.KkangtongEntity
import com.wagwan.faketimes.domain.user.dto.User
import org.springframework.stereotype.Component

data class Kkangtong(
    val title: String,
    val author: String,
    val image: String,
    val url: String? = null,
    ){
    @Component
    companion object {
        fun toKkangtong(entity: KkangtongEntity): Kkangtong {
            return Kkangtong(
                title = entity.title,
                author = entity.author,
                image = entity.image,
                url = entity.url,
            )
        }
    }
}