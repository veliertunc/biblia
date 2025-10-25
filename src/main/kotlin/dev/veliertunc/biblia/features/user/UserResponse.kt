package dev.veliertunc.biblia.features.user

import java.time.Instant
import java.util.UUID

data class UserResponse(
    val username: String,
    val email: String,
    val enabled: Boolean,
)