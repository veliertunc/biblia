package dev.veliertunc.biblia.discussion

import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional
import java.util.UUID

interface DiscussionRepository : JpaRepository<Discussion, UUID> {
    fun findAllByTopic(topic: String): List<Discussion>
    fun findAllByTags_NameInIgnoreCase(name: Collection<String>): List<Discussion>

    fun findByTags_NameIgnoreCase(name: String): List<Discussion>

    fun findDistinctByTags_NameInIgnoreCase(names: Collection<String>): List<Discussion>

    fun existsByTag_NameIgnoreCase(name: String): Boolean
    fun existsByTags_NameIgnoreCase(name: String): Boolean
}
