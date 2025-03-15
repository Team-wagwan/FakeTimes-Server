package com.wagwan.faketimes.domain.kkangtong.service

import com.wagwan.faketimes.domain.kkangtong.domain.entity.KkangtongEntity
import com.wagwan.faketimes.domain.kkangtong.domain.repository.KkangtongJpaRepository
import com.wagwan.faketimes.domain.kkangtong.dto.Kkangtong
import com.wagwan.faketimes.domain.kkangtong.dto.request.ChatGPTRequest
import com.wagwan.faketimes.domain.kkangtong.dto.response.ChatGPTResponse
import com.wagwan.faketimes.domain.user.util.UserSecurity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.FileSystemResource
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.client.RestTemplate
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.time.LocalDate
import kotlin.random.Random

@Service
class KkangtongService(
    private val repository: KkangtongJpaRepository,
    private val userSecurity: UserSecurity
) {

    @Autowired
    private lateinit var restTemplate: RestTemplate

    @Autowired
    @Qualifier("openaiConfig")
    private lateinit var template: RestTemplate

    @Value("\${openai.model}")
    private lateinit var model: String

    @Value("\${openai.api.url}")
    private lateinit var apiURL: String

    fun newsTitle(
        text: String,
    ): String {
        val request = ChatGPTRequest(model = model, prompt = "$text 를 주제로 SNS 스타일의 뉴스 기사 제목을 반환해줘", )
        val chatGPTResponse = template.postForObject(
            apiURL, request,
            ChatGPTResponse::class.java
        )
        val keyWord: String = chatGPTResponse?.choices?.get(0)?.message?.content
            ?: throw IllegalStateException("No response from AI")

        return keyWord
    }

    fun description(keyWord:String): String {
        val request = ChatGPTRequest(model = model, prompt = "$keyWord 를 주제로 SNS 스타일의 뉴스 기사를 반환해줘", )
        val chatGPTResponse = template.postForObject(
            apiURL, request,
            ChatGPTResponse::class.java
        )
        val description: String = chatGPTResponse?.choices?.get(0)?.message?.content
            ?: throw IllegalStateException("No response from AI")

        return description
    }

    fun hapchae(image: MultipartFile, topic: String): String {
        val url = "http://43.200.172.248:5000/upload"
        val title = newsTitle(topic)
        val description = description(topic)
        val author = userSecurity.getUser().name
        val tempFile = File.createTempFile("upload_", ".png")
        image.transferTo(tempFile)

        // Flask에서 파일을 올바르게 인식하도록 FileSystemResource 사용
        val fileResource = FileSystemResource(tempFile)

        // 요청 헤더 설정
        val headers = HttpHeaders()
        headers.contentType = MediaType.MULTIPART_FORM_DATA

        // Multipart 요청 생성
        val body = LinkedMultiValueMap<String, Any>()
        body.add("image", fileResource)  // Flask 서버에서 'image' 키로 받음
        body.add("text", title)       // Flask 서버에서 'text' 키로 받음

        val requestEntity = HttpEntity(body, headers)
        val response = restTemplate.postForEntity(url, requestEntity, Map::class.java)

        // Flask 응답에서 S3 URL 반환
        val res = response.body?.get("s3_url") as String

        val entity = repository.save(
            KkangtongEntity(
                title = title,
                description = description,
                author = author,
                image = res,
                read_count = Random.nextLong(1, 10000),
                date = LocalDate.now(),
            )
        )

        val real = repository.save(
            KkangtongEntity(
                id = entity.id,
                title = entity.title,
                description = entity.description,
                author = entity.author,
                image = entity.image,
                read_count = entity.read_count,
                date = LocalDate.now(),
                url = "http://54.180.120.162:8080/news-detail/${entity.id}"
            )
        )

        return real.url!!
    }

    fun getAllNews():List<Kkangtong>{
        return repository.findAll().map { Kkangtong.toKkangtong(it) }
    }

}