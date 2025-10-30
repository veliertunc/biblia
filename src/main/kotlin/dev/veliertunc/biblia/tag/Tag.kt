package dev.veliertunc.biblia.tag

import dev.veliertunc.biblia.core.BaseEntity
import dev.veliertunc.biblia.discussion.Discussion

import jakarta.persistence.*
import lombok.*

@Entity
@Table(name = "tags")
@NoArgsConstructor
@AllArgsConstructor
@Builder
class Tag(

    @Column(nullable = false, unique = true, length = 100)
    var name: String,

    @ManyToMany(mappedBy = "tags", fetch = FetchType.EAGER)
    var discussions: MutableSet<Discussion> = mutableSetOf()
) : BaseEntity()