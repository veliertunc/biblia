package dev.veliertunc.biblia.tag


import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import java.util.*


@Service
class TagService(
    private val tagRepo: TagRepository,
    private val tagMapper: TagMapper
) {

    /** Return existing tags or create missing ones for the given names. */
    fun resolveOrCreateTags(names: Set<String>): Set<Tag> =
        names.map { name ->
            tagRepo.findByName(name) ?: tagRepo.save(Tag(name = name))
        }.toSet()

    /** Simple read‑only lookup – useful for other parts of the app. */
    fun findByName(name: String): Tag? = tagRepo.findByName(name)

    /** Convert a Tag entity to its response DTO. */
    fun toResponse(tag: Tag): TagResponse = tagMapper.toResponse(tag)

    /** Create a new Tag from a CreateTagRequest. */
    fun create(req: CreateTagRequest): Tag = tagRepo.save(tagMapper.fromCreateRequest(req))

    /** Update an existing Tag – ignores null fields. */
    fun update(id: UUID, req: UpdateTagRequest): Tag {
        val tag = tagRepo.findById(id)
            .orElseThrow { EntityNotFoundException("Tag $id not found") }
        tagMapper.updateEntityFromDto(req, tag)
        return tagRepo.save(tag)
    }
}
