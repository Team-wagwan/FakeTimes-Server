package com.wagwan.faketimes.domain.auth.service.response

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_EMPTY)
data class RefreshTokenResponse(
    val accessToken: String
)