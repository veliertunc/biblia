package dev.veliertunc.biblia.core

import jakarta.persistence.*
import java.time.Instant
import java.util.UUID

@MappedSuperclass
abstract class BaseEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    var id: UUID? = null,

    @Column(nullable = false, updatable = false)
    var createdAt: Instant? = null,

    @Column(nullable = false)
    var updatedAt: Instant? = null
) {
    @PrePersist
    fun onCreate() {
        id = UUID.randomUUID()
        createdAt = Instant.now()
        updatedAt = createdAt
    }

    @PreUpdate
    fun onUpdate() {
        updatedAt = Instant.now()
    }
}
