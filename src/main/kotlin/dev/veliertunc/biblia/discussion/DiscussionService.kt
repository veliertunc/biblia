package dev.veliertunc.biblia.discussion

import dev.veliertunc.biblia.entry.CreateEntryRequest
import dev.veliertunc.biblia.entry.EntryService
import dev.veliertunc.biblia.tag.TagService

import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*


@Service
class DiscussionService(
    private val discussionRepo: DiscussionRepository,
    private val discussionMapper: DiscussionMapper,
    private val tagService: TagService,
    private val entryService: EntryService
) {

    fun toResponse(discussion: Discussion) = discussionMapper.toResponse(discussion)

    fun getAll(): List<Discussion?> = discussionRepo.findAll()

    fun getById(id: UUID): Discussion? = discussionRepo.findById(id)
        .orElseThrow { EntityNotFoundException("Discussion $id not found") }


    fun getDiscussionsByTag(name: String): List<Discussion> =
        discussionRepo.findAllByTags_NameIgnoreCase(name)

    fun getDiscussionsByAnyTags(names: List<String>): List<Discussion> =
        discussionRepo.findAllDistinctByTags_NameInIgnoreCase(names)

    fun getDiscussionsByAllTags(names: List<String>): List<Discussion> =
        discussionRepo.findAllByTags_NameInIgnoreCase(names)

    /** Create a new discussion ------------------------------------------------- */
    @Transactional
    fun create(req: CreateDiscussionRequest): DiscussionResponse {
        val discussion = discussionMapper.fromCreateRequest(req)

        // Resolve or create tags, then add them via the entity method
        // tagService.resolveOrCreateTags(req.tags).forEach { discussion.addTag(it) }

        return discussionRepo.save(discussion).let(discussionMapper::toResponse)
    }

    /** Update an existing discussion ------------------------------------------ */
    @Transactional
    fun update(id: UUID, req: UpdateDiscussionRequest): Discussion {
        val discussion = discussionRepo.findById(id)
            .orElseThrow { EntityNotFoundException("Discussion $id not found") }

        // Apply scalar updates (topic, etc.)
        discussionMapper.updateEntityFromDto(req, discussion)

        return discussionRepo.save(discussion)
    }

    @Transactional
    fun delete(id: UUID) {
        if (!discussionRepo.existsById(id))
            throw EntityNotFoundException("Discussion not found")
        discussionRepo.deleteById(id)
    }

    /** Explicitly add a single tag ------------------------------------------- */
    @Transactional
    fun addTag(discussionId: UUID, tagName: String): Discussion {
        val discussion = discussionRepo.findById(discussionId)
            .orElseThrow { EntityNotFoundException("Discussion $discussionId not found") }

        val tag = tagService.resolveOrCreateTags(setOf(tagName)).first()
        discussion.addTag(tag)

        return discussionRepo.save(discussion)
    }

    /** Explicitly remove a single tag ---------------------------------------- */
    @Transactional
    fun removeTag(discussionId: UUID, tagName: String): Discussion {
        val discussion = discussionRepo.findById(discussionId)
            .orElseThrow { EntityNotFoundException("Discussion $discussionId not found") }

        val tag = tagService.getByName(tagName)
            ?: throw EntityNotFoundException("Tag '$tagName' does not exist")

        discussion.removeTag(tag)

        return discussionRepo.save(discussion)
    }


    /** Explicitly add a single entry ------------------------------------------- */
    @Transactional
    fun addEntry(discussionId: UUID, req: CreateEntryRequest): Discussion {
        val discussion = discussionRepo.findById(discussionId)
            .orElseThrow { EntityNotFoundException("Discussion $discussionId not found") }

        val entry = entryService.create(req)

        discussion.addEntry(entry)

        return discussionRepo.save(discussion)
    }

    /** Explicitly remove a single entry ---------------------------------------- */
    @Transactional
    fun removeEntry(discussionId: UUID, entryId: UUID): Discussion {
        val discussion = discussionRepo.findById(discussionId)
            .orElseThrow { EntityNotFoundException("Discussion $discussionId not found") }

        val entry = entryService.getById(entryId)

        discussion.removeEntry(entry)

        return discussionRepo.save(discussion)
    }

}