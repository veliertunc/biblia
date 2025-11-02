package dev.veliertunc.biblia.entry

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface EntryRepository : JpaRepository<Entry, UUID> {
    fun findByName(name: String): Entry?
}