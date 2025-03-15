package com.wagwan.faketimes.domain.kkangtong.domain.entity

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "tb_kangtong")
class KkangtongEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var title: String,
    var author: String,
    @Column(columnDefinition = "TEXT")
    var description: String,
    var image: String,
    var read_count: Long,
    var date: LocalDate,
    var url: String? = null,
)