package dev.veliertunc.biblia.features.user

data class UpdateUserRequest(
    val email: String? = null,
    val password: String? = null,
    val enabled: Boolean? = null
)