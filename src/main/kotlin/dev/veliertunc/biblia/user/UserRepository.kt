package dev.veliertunc.biblia.user

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface UserRepository : JpaRepository<User, UUID> {
    fun findAllByRoles_Name(roleName: String): List<User>

    fun findByUsername(username: String): User?
    fun findByEmail(email: String): User?

    fun existsByEmail(email: String): Boolean
    fun existsByUsername(username: String): Boolean
}
