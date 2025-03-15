package com.wagwan.faketimes.domain.auth.client.request

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern

data class SignUpRequest(
    @JsonProperty("email")
    @Email
    val email: String,
    @JsonProperty("name")
    @NotBlank(message = "이름 안넣음")
    val name: String,
    @JsonProperty("password")
    @NotBlank(message = "비번 안넣음")
    val password: String,
)