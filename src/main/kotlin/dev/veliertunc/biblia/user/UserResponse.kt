package dev.veliertunc.biblia.user

import java.time.Instant
import java.util.UUID

data class UserResponse(
    val id: UUID,
    val createdAt: Instant,
    val updatedAt: Instant,
    val username: String,
    val email: String,
    val enabled: Boolean,
    val roles: Set<UUID>,//Role IDs
)