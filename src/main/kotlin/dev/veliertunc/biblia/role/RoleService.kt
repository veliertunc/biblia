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

    fun toResponse(role: Role) = roleMapper.toResponse(role)

    fun getAll(): List<Role?> = roleRepo.findAll()

    fun getById(id: UUID): Role? = roleRepo.findById(id)
        .orElseThrow { EntityNotFoundException("User $id not found") }

    @Transactional
    fun create(req: CreateRoleRequest): Role {
        val role = roleMapper.fromCreateRequest(req)

        return roleRepo.save(role)
    }

    @Transactional
    fun update(id: UUID, req: UpdateRoleRequest): Role {
        val role = roleRepo.findById(id)
            .orElseThrow { EntityNotFoundException("Role $id not found") }

        roleMapper.updateEntityFromDto(req, role)

        return roleRepo.save(role)
    }

    @Transactional
    fun delete(id: UUID) {
        if (!roleRepo.existsById(id))
            throw EntityNotFoundException("Role $id not found")
        roleRepo.deleteById(id)
    }

}