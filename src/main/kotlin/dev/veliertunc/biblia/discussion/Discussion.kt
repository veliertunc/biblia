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
        joinColumns = [JoinColumn(name = "discussion")],
        inverseJoinColumns = [JoinColumn(name = "tag_id")]
    )
    var tags: MutableSet<Tag> = mutableSetOf()

) : BaseEntity() {

    /** Add a tag and keep the inverse side in sync */
    fun addTag(tag: Tag): Boolean {
        val added = tags.add(tag)
        if (added) {
            tag.discussions.add(this)   // <-- keep the other side consistent
        }
        return added
    }

    /** Remove a tag and keep the inverse side in sync */
    fun removeTag(tag: Tag): Boolean {
        val removed = tags.remove(tag)
        if (removed) {
            tag.discussions.remove(this)   // <-- keep the other side consistent
        }
        return removed
    }

    /** Entry helpers (same idea) */
    fun addEntry(entry: Entry): Boolean {
        val added = entries.add(entry)
        if (added) {
            entry.discussion = this
        }
        return added
    }

    fun removeEntry(entry: Entry): Boolean {
        val removed = entries.remove(entry)
        if (removed) {
            entry.discussion = null
        }
        return removed
    }

}