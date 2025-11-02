package dev.veliertunc.biblia.tag


import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import java.util.*


@Service
class TagService(
    private val tagRepo: TagRepository,
    private val tagMapper: TagMapper
) {

    fun toResponse(tag: Tag): TagResponse = tagMapper.toResponse(tag)

    /** Return existing tags or create missing ones for the given names. */
    fun resolveOrCreateTags(names: Set<String>): Set<Tag> =
        names.map { name ->
            tagRepo.findByName(name) ?: tagRepo.save(Tag(name = name))
        }.toSet()

    fun getAll() = tagRepo.findAll()

    fun getById(id: UUID) = tagRepo.findById(id)

    fun getByName(name: String): Tag? = tagRepo.findByName(name)

    fun create(req: CreateTagRequest): Tag = tagRepo.save(tagMapper.fromCreateRequest(req))

    fun update(id: UUID, req: UpdateTagRequest): Tag {
        val tag = tagRepo.findById(id)
            .orElseThrow { EntityNotFoundException("Tag $id not found") }
        tagMapper.updateEntityFromDto(req, tag)
        return tagRepo.save(tag)
    }

    fun detete(id: UUID) {
        if (!tagRepo.existsById(id))
            throw EntityNotFoundException("User not found")

        tagRepo.deleteById(id)
    }
}
