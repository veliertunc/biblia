package dev.veliertunc.biblia.user

import dev.veliertunc.biblia.discussion.CreateDiscussionRequest
import dev.veliertunc.biblia.discussion.DiscussionService
import dev.veliertunc.biblia.discussion.UpdateDiscussionRequest

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
    fun update(@PathVariable id: UUID, @RequestBody req: UpdateDiscussionRequest) = discussionService.update(id, req)

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: UUID) = discussionService.delete(id)

    @DeleteMapping("/{discussionId}/tags/{tagName}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteTagFromDiscussion(
        @PathVariable discussionId: UUID,
        @PathVariable tagName: String
    ) = discussionService.removeTag(discussionId, tagName)
}