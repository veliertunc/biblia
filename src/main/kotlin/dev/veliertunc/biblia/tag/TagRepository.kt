package dev.veliertunc.biblia.tag

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.transaction.annotation.Transactional
import java.util.Optional
import java.util.UUID

interface TagRepository : JpaRepository<Tag, UUID> {
    fun findAllByNameIn(names: Collection<String>): List<Tag>

    fun findByName(name: String): Tag?
    fun findByNameIgnoreCase(name: String): Tag?

    fun existsByNameIgnoreCase(name: String): Boolean
}
