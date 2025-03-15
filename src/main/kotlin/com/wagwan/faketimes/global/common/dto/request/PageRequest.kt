package com.wagwan.faketimes.global.common.dto.request

import org.jetbrains.annotations.NotNull

data class PageRequest(
    @NotNull("page is empty")
    var page: Long = 1,
    @NotNull("size is 10")
    var size: Long = 15,
)