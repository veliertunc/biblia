package dev.veliertunc.biblia.role

import dev.veliertunc.biblia.role.RoleMapper
import dev.veliertunc.biblia.role.RoleRepository


import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*


@Service
class RoleService(
    private val roleRepo: RoleRepository,
    private val roleMapper: RoleMapper
) {

    fun getAll(): List<RoleResponse> =
        roleRepo.findAll().map { it.let(roleMapper::toResponse) }

    fun getById(id: UUID): RoleResponse =
        roleRepo.findById(id)
            .orElseThrow { EntityNotFoundException("User $id not found") }
            .let(roleMapper::toResponse)

    @Transactional
    fun create(req: CreateRoleRequest): RoleResponse {
        val role = roleMapper.fromCreateRequest(req)

        return roleRepo.save(role).let(roleMapper::toResponse)
    }

    @Transactional
    fun update(id: UUID, req: UpdateRoleRequest): RoleResponse {
        val role = roleRepo.findById(id)
            .orElseThrow { EntityNotFoundException("Role $id not found") }

        roleMapper.updateEntityFromDto(req, role)

        return roleRepo.save(role).let(roleMapper::toResponse)
    }

    @Transactional
    fun delete(id: UUID) {
        if (!roleRepo.existsById(id))
            throw EntityNotFoundException("Role $id not found")
        roleRepo.deleteById(id)
    }

}