package dev.veliertunc.biblia.tag

import dev.veliertunc.biblia.core.BaseEntity
import dev.veliertunc.biblia.discussion.Discussion

import jakarta.persistence.*
import lombok.*

@Entity
@Table(name = "tags")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
class Tag(

    @Column(nullable = false, unique = true, length = 100)
    var name: String,

    @ManyToMany(mappedBy = "tags")
    var discussions: MutableSet<Discussion> = mutableSetOf()

) : BaseEntity() {


    override fun equals(other: Any?): Boolean {
        if (this === other) return true                     // same reference
        if (other == null || this::class != other::class) return false

        other as Tag

        // Prefer natural key (`name`) when both are non‑null
        if (name.isNotEmpty() && other.name.isNotEmpty()) {
            return name.lowercase() == other.name.lowercase()
        }

        // Otherwise compare the generated UUID (may be null for transient objects)
        return id == other.id
    }

    override fun hashCode(): Int = if (name.isNotEmpty()) name.hashCode() else id.hashCode()

}