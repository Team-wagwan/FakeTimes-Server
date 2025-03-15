package com.wagwan.faketimes.global.security.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate
import org.springframework.http.client.ClientHttpRequestInterceptor

@Configuration
class OpenAIConfig {
    @Value("\${openai.api.key}")
    private val openAiKey: String? = null

    @Bean(name = ["openaiConfig"])
    fun restTemplate(): RestTemplate {
        val restTemplate = RestTemplate()
        restTemplate.interceptors.add(ClientHttpRequestInterceptor { request, body, execution ->
            request.headers.add("Authorization", "Bearer $openAiKey")
            execution.execute(request, body)
        })
        return restTemplate
    }
}