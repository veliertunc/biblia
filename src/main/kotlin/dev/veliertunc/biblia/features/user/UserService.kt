package dev.veliertunc.biblia.features.user

import jakarta.persistence.EntityNotFoundException
import lombok.RequiredArgsConstructor
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@RequiredArgsConstructor
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {

    fun getAll(): List<UserResponse> =
        userRepository.findAll().map { it.toResponse() }

    fun getById(id: UUID): UserResponse =
        userRepository.findById(id)
            .orElseThrow { EntityNotFoundException("User not found") }
            .toResponse()

    @Transactional
    fun create(req: CreateUserRequest): UserResponse {
        require(!userRepository.existsByUsername(req.username)) { "Username already taken" }
        require(!userRepository.existsByEmail(req.email)) { "Email already registered" }

        val user = User(
            username = req.username,
            email = req.email,
            passwordHash = passwordEncoder.encode(req.password)
        )
        return userRepository.save(user).toResponse()
    }

    @Transactional
    fun update(id: UUID, req: UpdateUserRequest): UserResponse {
        val user = userRepository.findById(id)
            .orElseThrow { EntityNotFoundException("User not found") }

        req.email?.let {
            if (it != user.email && userRepository.existsByEmail(it))
                throw IllegalArgumentException("Email already in use")
            user.email = it
        }
        req.password?.let { user.passwordHash = passwordEncoder.encode(it) }
        req.enabled?.let { user.enabled = it }

        return userRepository.save(user).toResponse()
    }

    @Transactional
    fun delete(id: UUID) {
        if (!userRepository.existsById(id))
            throw EntityNotFoundException("User not found")
        userRepository.deleteById(id)
    }

    private fun User.toResponse() = UserResponse(
        username = this.username,
        email = this.email,
        enabled = this.enabled,
    )
}
