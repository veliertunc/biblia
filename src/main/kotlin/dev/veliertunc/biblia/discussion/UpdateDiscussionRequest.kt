package dev.veliertunc.biblia.discussion

data class UpdateDiscussionRequest(
    val topic: String? = null,
    val tags: Set<String>? = null
)