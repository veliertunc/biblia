package dev.veliertunc.biblia.discussion

import dev.veliertunc.biblia.tag.Tag
import dev.veliertunc.biblia.tag.TagRepository
import dev.veliertunc.biblia.tag.TagService

import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*


@Service
class DiscussionService(
    private val discussionRepo: DiscussionRepository,
    private val discussionMapper: DiscussionMapper,
    private val tagService: TagService
) {
    fun getAll(): List<DiscussionResponse> =
        discussionRepo.findAll().map { it.let(discussionMapper::toResponse) }

    fun getById(id: UUID): DiscussionResponse =
        discussionRepo.findById(id)
            .orElseThrow { EntityNotFoundException("User not found") }
            .let(discussionMapper::toResponse)

    fun getDiscussionsByTag(name: String): List<DiscussionResponse> =
        discussionRepo.findByTags_NameIgnoreCase(name)
            .map(discussionMapper::toResponse)

    fun getDiscussionsByAnyTags(names: List<String>): List<DiscussionResponse> =
        discussionRepo.findDistinctByTags_NameInIgnoreCase(names)
            .map(discussionMapper::toResponse)

    fun getDiscussionsByAllTags(names: List<String>): List<DiscussionResponse> =
        discussionRepo.findAllByTags_NameInIgnoreCase(names)
            .map(discussionMapper::toResponse)

    /** Create a new discussion ------------------------------------------------- */
    fun create(req: CreateDiscussionRequest): DiscussionResponse {
        val discussion = discussionMapper.fromCreateRequest(req)

        // Resolve or create tags, then add them via the entity method
        tagService.resolveOrCreateTags(req.tags).forEach { discussion.addTag(it) }

        discussionRepo.save(discussion)
        return discussionMapper.toResponse(discussion)
    }

    /** Update an existing discussion ------------------------------------------ */
    fun update(id: UUID, req: UpdateDiscussionRequest): DiscussionResponse {
        val discussion = discussionRepo.findById(id)
            .orElseThrow { EntityNotFoundException("Discussion $id not found") }

        // Apply scalar updates (topic, etc.)
        discussionMapper.updateEntityFromDto(req, discussion)

        // ----- Tag handling ----------------------------------------------------
        // Replace tags only if the request supplies a non‑null set
        req.tags?.let { incomingTagNames ->
            // Resolve/create the incoming tags first
            val incomingTags = tagService.resolveOrCreateTags(incomingTagNames)

            // Remove tags that are no longer present
            discussion.tags
                .filter { it !in incomingTags }
                .forEach { discussion.removeTag(it) }

            // Add any new tags
            incomingTags
                .filter { it !in discussion.tags }
                .forEach { discussion.addTag(it) }
        }

        discussionRepo.save(discussion)
        return discussionMapper.toResponse(discussion)
    }

    @Transactional
    fun delete(id: UUID) {
        if (!discussionRepo.existsById(id))
            throw EntityNotFoundException("Discussion not found")
        discussionRepo.deleteById(id)
    }

    /** Explicitly add a single tag ------------------------------------------- */
    fun addTag(discussionId: UUID, tagName: String): DiscussionResponse {
        val discussion = discussionRepo.findById(discussionId)
            .orElseThrow { EntityNotFoundException("Discussion $discussionId not found") }

        val tag = tagService.resolveOrCreateTags(setOf(tagName)).first()
        discussion.addTag(tag)

        discussionRepo.save(discussion)
        return discussionMapper.toResponse(discussion)
    }

    /** Explicitly remove a single tag ---------------------------------------- */
    fun removeTag(discussionId: UUID, tagName: String): DiscussionResponse {
        val discussion = discussionRepo.findById(discussionId)
            .orElseThrow { EntityNotFoundException("Discussion $discussionId not found") }

        val tag = tagService.findByName(tagName)
            ?: throw EntityNotFoundException("Tag '$tagName' does not exist")

        discussion.removeTag(tag)

        discussionRepo.save(discussion)
        return discussionMapper.toResponse(discussion)
    }
}