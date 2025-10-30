package dev.veliertunc.biblia.discussion

data class DiscussionResponse(
    val topic: String,
    val tags: Set<String>
)
