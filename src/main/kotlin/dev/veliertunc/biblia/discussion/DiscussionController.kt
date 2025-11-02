package dev.veliertunc.biblia.user

import dev.veliertunc.biblia.discussion.CreateDiscussionRequest
import dev.veliertunc.biblia.discussion.DiscussionService
import dev.veliertunc.biblia.discussion.UpdateDiscussionRequest
import dev.veliertunc.biblia.entry.CreateEntryRequest
import dev.veliertunc.biblia.tag.CreateTagRequest

import lombok.RequiredArgsConstructor
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/discussions")
class DiscussionController(
    private val discussionService: DiscussionService
) {
    @GetMapping
    fun list() = discussionService.getAll()

    @GetMapping("/{id}")
    fun get(@PathVariable id: UUID) = discussionService.getById(id)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody req: CreateDiscussionRequest) = discussionService.create(req)

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun update(
        @PathVariable id: UUID,
        @RequestBody req: UpdateDiscussionRequest
    ) = discussionService.update(id, req)

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: UUID) = discussionService.delete(id)

    // Tag add & removal
    @PostMapping("/{discussion}/tags/{tagName}")
    @ResponseStatus(HttpStatus.CREATED)
    fun addTagToDiscussion(
        @PathVariable discussionId: UUID,
        @PathVariable tagName: String
    ) = discussionService.addTag(discussionId, tagName)

    @DeleteMapping("/{discussion}/tags/{tagName}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteTagFromDiscussion(
        @PathVariable discussionId: UUID,
        @PathVariable tagName: String
    ) = discussionService.removeTag(discussionId, tagName)


    // Entry add & removal
    @PostMapping("/{discussion}/entries")
    @ResponseStatus(HttpStatus.CREATED)
    fun addTagToDiscussion(
        @PathVariable discussionId: UUID,
        @RequestBody entryReq: CreateEntryRequest
    ) = discussionService.addEntry(discussionId, entryReq)

    @DeleteMapping("/{discussion}/entries/{entryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteTagFromDiscussion(
        @PathVariable discussionId: UUID,
        @PathVariable entryId: UUID
    ) = discussionService.removeEntry(discussionId, entryId)

}