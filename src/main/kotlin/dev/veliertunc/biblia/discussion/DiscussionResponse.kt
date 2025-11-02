package dev.veliertunc.biblia.discussion

import java.time.Instant
import java.util.UUID

data class DiscussionResponse(
    val id: UUID,
    val createdAt: Instant,
    val updatedAt: Instant,
    val topic: String,
)
