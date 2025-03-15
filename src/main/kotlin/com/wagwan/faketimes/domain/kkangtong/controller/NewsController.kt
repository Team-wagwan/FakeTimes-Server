package com.wagwan.faketimes.domain.kkangtong.controller

import com.wagwan.faketimes.domain.kkangtong.domain.repository.KkangtongJpaRepository
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/news-detail")
class NewsController(
    private val kkangtongJpaRepository: KkangtongJpaRepository
) {

    @GetMapping("/{id}")
    fun news(@PathVariable("id") id: Long, model: Model):String {
        val entity = kkangtongJpaRepository.findById(id).orElse(null)
        if (entity == null) throw RuntimeException("id is null")
        else {
            model.addAttribute("entity", entity)
//            model.addAttribute("content", entity.description)
//            model.addAttribute("author", entity.author)
//            model.addAttribute("imageUrl", entity.image)
//            model.addAttribute("read", entity.read_count)
//            model.addAttribute("createdAt", entity.date)
            return "news"
        }
    }

}