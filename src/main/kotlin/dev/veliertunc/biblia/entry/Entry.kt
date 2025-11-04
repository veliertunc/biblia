package dev.veliertunc.biblia.entry

import jakarta.persistence.*
import lombok.*

import dev.veliertunc.biblia.core.BaseEntity
import dev.veliertunc.biblia.discussion.Discussion

@Entity
@Table(name = "entries")
@Getter
@Setter
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