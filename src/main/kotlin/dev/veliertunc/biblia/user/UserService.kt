package dev.veliertunc.biblia.user

import jakarta.persistence.EntityNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val userMapper: UserMapper
) {

    fun getAll(): List<UserResponse> =
        userRepository.findAll().map { it.let(userMapper::toResponse) }

    fun getById(id: UUID): UserResponse =
        userRepository.findById(id)
            .orElseThrow { EntityNotFoundException("User not found") }
            .let(userMapper::toResponse)

    @Transactional
    fun create(req: CreateUserRequest): UserResponse {
        require(!userRepository.existsByUsername(req.username)) { "Username already taken" }
        require(!userRepository.existsByEmail(req.email)) { "Email already registered" }

        val user = userMapper.fromCreateRequest(req)
        user.passwordHash = passwordEncoder.encode(req.password)

        return userRepository.save(user).let(userMapper::toResponse)
    }

    @Transactional
    fun update(id: UUID, req: UpdateUserRequest): UserResponse {
        val user = userRepository.findById(id)
            .orElseThrow { EntityNotFoundException("User not found") }

        userMapper.updateEntityFromDto(req, user)

        req.password?.let { user.passwordHash = passwordEncoder.encode(it) }

        return userRepository.save(user).let(userMapper::toResponse)
    }

    @Transactional
    fun delete(id: UUID) {
        if (!userRepository.existsById(id))
            throw EntityNotFoundException("User not found")
        userRepository.deleteById(id)
    }

}
