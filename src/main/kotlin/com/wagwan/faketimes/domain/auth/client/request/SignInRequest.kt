package com.wagwan.faketimes.domain.auth.client.request

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class SignInRequest(
    @JsonProperty("email")
    @Email
    val email: String,
    @JsonProperty("password")
    @NotBlank(message = "비번 안넣음")
    val password: String
)