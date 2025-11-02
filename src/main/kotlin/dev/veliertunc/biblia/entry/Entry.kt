package dev.veliertunc.biblia.entry

import jakarta.persistence.*
import lombok.NoArgsConstructor
import lombok.AllArgsConstructor
import lombok.Builder

import dev.veliertunc.biblia.core.BaseEntity
import dev.veliertunc.biblia.discussion.Discussion

@Entity
@Table(name = "entries")
@NoArgsConstructor
@AllArgsConstructor
@Builder
class Entry(

    @Column(nullable = false)
    var text: String,

    @ManyToOne
    @JoinColumn(name = "discussion")
    var discussion: Discussion? = null

) : BaseEntity()