package dev.veliertunc.biblia.user

import lombok.RequiredArgsConstructor
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
class UserController(
    private val userService: UserService
) {

    @GetMapping
    fun list() = userService.getAll().map { it.let(userService::toResponse) }

    @GetMapping("/{id}")
    fun get(@PathVariable id: UUID) =
        userService.getById(id).let(userService::toResponse)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody req: CreateUserRequest) =
        userService.create(req).let(userService::toResponse)

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: UUID,
        @RequestBody req: UpdateUserRequest
    ) = userService.update(id, req).let(userService::toResponse)

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: UUID) = userService.delete(id)
}