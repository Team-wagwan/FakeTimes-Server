package com.wagwan.faketimes.domain.kkangtong.controller

import com.wagwan.faketimes.domain.kkangtong.domain.entity.KkangtongEntity
import com.wagwan.faketimes.domain.kkangtong.service.KkangtongService
import com.wagwan.faketimes.global.common.dto.response.BaseResponseData
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/kkangtong")
class KkangtongController(
    private val service: KkangtongService
) {

    @PostMapping
    fun chat(@RequestPart text:String, @RequestPart file: MultipartFile): BaseResponseData<String> {
        val id = service.hapchae(topic = text, image = file)
        return BaseResponseData.ok(
            message = "url 생성 성공",
            data = id
        )
    }

    @GetMapping
    fun getAll():BaseResponseData<List<KkangtongEntity>> {
        return BaseResponseData.ok(
            message = "조회 성공",
            data = service.getAllNews()
        )
    }

}