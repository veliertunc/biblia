package dev.veliertunc.biblia.user

data class UserResponse(
    val username: String,
    val email: String,
    val enabled: Boolean,
)