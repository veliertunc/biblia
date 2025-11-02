package dev.veliertunc.biblia.tag

import java.time.Instant
import java.util.UUID

data class TagResponse(
    val id: UUID,
    val createdAt: Instant,
    val updatedAt: Instant,
    val name: String,
)