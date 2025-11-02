package dev.veliertunc.biblia.core

data class ErrorResponse(
    val timestamp: Long = System.currentTimeMillis(),
    val status: Int,
    val error: String,
    val message: String?,
    val path: String?
)
