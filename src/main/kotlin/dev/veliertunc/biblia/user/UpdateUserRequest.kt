package dev.veliertunc.biblia.user

data class UpdateUserRequest(
    val email: String? = null,
    val password: String? = null,
    val enabled: Boolean? = null
)