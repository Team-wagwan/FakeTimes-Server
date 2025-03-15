package com.wagwan.faketimes.domain.user.controller

import com.wagwan.faketimes.domain.user.dto.User
import com.wagwan.faketimes.domain.user.util.UserSecurity
import com.wagwan.faketimes.global.common.dto.response.BaseResponseData
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class UserController(
    private val userSecurity: UserSecurity
) {
    @GetMapping
    fun getProfile(): BaseResponseData<User> {
        return BaseResponseData.ok(
            message = "조회 성공",
            data = userSecurity.getUser()
        )
    }
}