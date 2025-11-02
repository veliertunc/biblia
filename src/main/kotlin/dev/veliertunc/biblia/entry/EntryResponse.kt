package dev.veliertunc.biblia.entry

import java.time.Instant
import java.util.UUID

data class EntryResponse(
    val id: UUID,
    val createdAt: Instant,
    val updatedAt: Instant,
    val name: String,
)