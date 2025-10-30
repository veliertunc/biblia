package dev.veliertunc.biblia.discussion

import dev.veliertunc.biblia.core.BaseEntity
import dev.veliertunc.biblia.entry.Entry
import dev.veliertunc.biblia.tag.Tag

import jakarta.persistence.*
import lombok.NoArgsConstructor
import lombok.AllArgsConstructor
import lombok.Builder

@Entity
@Table(name = "discussions")
@NoArgsConstructor
@AllArgsConstructor
@Builder
class Discussion(

    @Column(nullable = false, length = 255)
    var topic: String,

    @OneToMany(mappedBy = "discussion")
    var entries: MutableSet<Entry> = mutableSetOf(),

    @ManyToMany(fetch = FetchType.EAGER, cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    @JoinTable(
        name = "discussion_tags",
        joinColumns = [JoinColumn(name = "discussion_id")],
        inverseJoinColumns = [JoinColumn(name = "tag_id")]
    )
    var tags: MutableSet<Tag> = mutableSetOf()

) : BaseEntity() {
    /** Helper to keep both sides in sync */
    fun addTag(tag: Tag) = tags.add(tag)
    fun removeTag(tag: Tag) = tags.remove(tag)

    fun addEntry(entry: Entry) = entries.add(entry)
    fun removeEntry(entry: Entry) = entries.remove(entry)
}