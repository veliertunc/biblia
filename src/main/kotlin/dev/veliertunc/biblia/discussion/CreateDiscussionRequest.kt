package dev.veliertunc.biblia.discussion

data class CreateDiscussionRequest(
    val topic: String,
    val tags: Set<String> = emptySet()
)