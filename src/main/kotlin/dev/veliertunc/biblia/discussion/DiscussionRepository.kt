package dev.veliertunc.biblia.discussion

import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional
import java.util.UUID

interface DiscussionRepository : JpaRepository<Discussion, UUID> {

    fun findAllByTopic(topic: String): List<Discussion>

    fun findAllByTags_NameIgnoreCase(name: String): List<Discussion>
    fun findAllDistinctByTags_NameInIgnoreCase(names: Collection<String>): List<Discussion>
    fun findAllByTags_NameInIgnoreCase(names: Collection<String>): List<Discussion>

    fun findByTags_NameIgnoreCase(name: String): Discussion?

    fun existsByTags_NameIgnoreCase(name: String): Boolean
}
