package com.wagwan.faketimes.domain.kkangtong.domain.repository

import com.wagwan.faketimes.domain.kkangtong.domain.entity.KkangtongEntity
import org.springframework.data.jpa.repository.JpaRepository

interface KkangtongJpaRepository: JpaRepository<KkangtongEntity, Long> {
}